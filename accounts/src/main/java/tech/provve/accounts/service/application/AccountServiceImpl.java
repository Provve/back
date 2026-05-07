package tech.provve.accounts.service.application;

import io.avaje.config.Config;
import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.exception.*;
import tech.provve.accounts.mapper.AccountMapper;
import tech.provve.accounts.mapper.AccountResponseMapper;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.accounts.service.JwtIssuingService;
import tech.provve.accounts.service.PasswordHashingService;
import tech.provve.api.server.generated.dto.*;
import tech.provve.libs.s3.S3Service;
import tech.provve.libs.scheduling.Scheduling;
import tech.provve.notification.domain.value.AccountDowngraded;
import tech.provve.notification.domain.value.AccountUpgraded;
import tech.provve.notification.domain.value.RecipientRequisites;
import tech.provve.notification.domain.value.ResetCode;
import tech.provve.notification.service.NotificationSendingService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static java.util.Objects.requireNonNullElseGet;
import static tech.provve.accounts.service.JwsParsingService.JWT_SUBJECT;
import static tech.provve.accounts.service.JwsParsingService.PREMIUM;

@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final JwtIssuingService jwtIssuingService;

    private final JwsParsingService jwsParsingService;

    private final PasswordHashingService passwordHashingService;

    @External
    private final NotificationSendingService notificationService;

    @External
    private final Scheduling scheduling;

    @External
    private final S3Service s3Service;

    @Override
    public void register(RegisterAccountRequest registerAccountRequest) {
        repository.findByLogin(registerAccountRequest.getLogin())
                  .ifPresent(_ -> {
                      throw new AccountAlreadyExists("Account with login '%s' is already exists.".formatted(
                              registerAccountRequest.getLogin()));
                  });
        repository.findByEmail(registerAccountRequest.getEmail())
                  .ifPresent(_ -> {
                      throw new AccountAlreadyExists("Account with email '%s' is already exists.".formatted(
                              registerAccountRequest.getEmail()));
                  });

        repository.save(AccountMapper.INSTANCE.map(
                registerAccountRequest, passwordHashingService.hash(
                        registerAccountRequest.getPassword())
        ));
    }

    @Override
    public void delete(DeleteAccountRequest deleteAccountRequest) {
        var login = jwsParsingService.parseAuth(deleteAccountRequest.getAuthToken(), JWT_SUBJECT);
        repository.delete(login);
    }

    @Override
    public String authenticate(AuthenticateUserRequest authenticateUserRequest) {
        Account account = repository.findByLogin(authenticateUserRequest.getLogin())
                                    .orElseThrow(() -> new AccountNotFound(String.format(
                                            "Account with login '%s' not found",
                                            authenticateUserRequest.getLogin()
                                    )));

        boolean invalidPasswordHash = !(passwordHashingService.verify(
                authenticateUserRequest.getPassword(),
                account.passwordHash()
        ));
        if (invalidPasswordHash) {
            throw new AccessDenied(String.format(
                    "Invalid password for '%s' account",
                    authenticateUserRequest.getLogin()
            ));
        }

        return jwtIssuingService.issueAuth(account.login(), account.isPremium());
    }

    @Override
    public void requestResetCode(String email) {
        var account = repository.findByEmail(email)
                                .orElseThrow(() -> new AccountNotFound(String.format(
                                        "Account with email '%s' not found",
                                        email
                                )));
        var resetToken = jwtIssuingService.issueReset(account.login());

        notificationService.send(
                new ResetCode(
                        new RecipientRequisites(account.login(), email),
                        resetToken
                ));
    }

    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        var login = jwsParsingService.parseReset(updatePasswordRequest.getResetToken(), JWT_SUBJECT);
        repository.updatePasswordHash(
                passwordHashingService.hash(updatePasswordRequest.getNewPassword()),
                login
        );
    }

    @Override
    public void updateEmail(UpdateEmailRequest updateEmailRequest) {
        var login = jwsParsingService.parseAuth(updateEmailRequest.getAuthToken(), JWT_SUBJECT);
        repository.findByEmail(updateEmailRequest.getEmail())
                  .ifPresent(_ -> {
                      throw new DataNotUnique("Email '%s' is not unique!".formatted(updateEmailRequest.getEmail()));
                  });
        repository.findByLogin(login)
                  .ifPresent(account -> {
                      if (!account.isConsentPersonalData()) {
                          throw new NoPersonalDataConsent();
                      }
                      repository.updateEmail(login, updateEmailRequest.getEmail());
                  });
    }

    @Override
    @SneakyThrows
    public void updateAvatar(UpdateAvatarRequest updateAvatarRequest) {
        var login = jwsParsingService.parseAuth(updateAvatarRequest.getAuthToken(), JWT_SUBJECT);
        String avatarPath = updateAvatarRequest.getAvatar()
                                               .uploadedFileName();

        byte[] avatar = Files.readAllBytes(Path.of(avatarPath));
        String bucket = Config.get("s3.buckets.images");
        String avatarUrl = s3Service.upload(
                bucket,
                S3Service.defaultKeygen(avatar),
                avatar
        );
        repository.updateAvatarUrl(login, avatarUrl);
    }

    @Override
    public void updateContacts(UpdateContactsRequest updateContactsRequest) {
        var login = jwsParsingService.parseAuth(updateContactsRequest.getAuthToken(), JWT_SUBJECT);
        String contacts = updateContactsRequest.getContacts()
                                               .getUrLs()
                                               .toString();
        repository.updateContactInfo(login, contacts);
    }

    @Override
    public void updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest) {
        var login = jwsParsingService.parseAuth(updatePersonalDataConsentRequest.getAuthToken(), JWT_SUBJECT);
        boolean consent = updatePersonalDataConsentRequest.getConsentPersonalData();

        repository.updatePersonalDataConsent(login, consent);
        if (!consent) {
            repository.updateEmail(login, null);
        }
    }

    @Override
    public void upgrade(String login) {
        var account = repository.findByLogin(login)
                                .orElseThrow(() -> new AccountNotFound(String.format(
                                        "Account with login '%s' not found",
                                        login
                                )));
        repository.updatePremium(login, true);
        scheduling.downgradePremiumAccount(
                login,
                Instant.now(Clock.systemUTC())
                       .plus(1, ChronoUnit.MONTHS)
        );
        notificationService.send(new AccountUpgraded(
                new RecipientRequisites(login, account.email())
        ));
    }

    @Override
    public void downgrade(String login) {
        repository.findByLogin(login)
                  .ifPresent(account -> {
                      repository.updatePremium(account.login(), false);
                      notificationService.send(new AccountDowngraded(new RecipientRequisites(
                              account.login(),
                              account.email()
                      )));
                  });
    }

    @Override
    public ProfilePublicView viewPublicProfile(String login) {
        var accountOptional = repository.findByLogin(login);
        String avatarUrl = accountOptional.map(Account::avatarUrl)
                                          .orElse(null);
        String username = accountOptional.map(account -> requireNonNullElseGet(account.username(), account::login))
                                         .orElse(null);
        String contactInfo = accountOptional.map(Account::contactInfo)
                                            .orElse(null);

        return new ProfilePublicView(username, avatarUrl, contactInfo);
    }

    @Override
    public ProfilePrivateView viewPrivateProfile(ViewPrivateProfile request) {
        var actualLogin = jwsParsingService.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        var login = request.getLogin();

        if (!Objects.equals(login, actualLogin)) {
            throw new AccessDenied("The Profile is not yours.");
        }
        var account = repository.findByLogin(login)
                                .orElseThrow(AccountNotFound::new); // маловероятно, пусть будет для информативности

        return AccountResponseMapper.INST.map(account);
    }

}
