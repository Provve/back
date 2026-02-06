package tech.provve.accounts.service.application;

import io.avaje.config.Config;
import io.avaje.inject.External;
import io.vertx.core.Vertx;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.domain.model.value.PremiumExpiration;
import tech.provve.accounts.exception.*;
import tech.provve.accounts.mapper.AccountMapper;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.accounts.service.JwtIssuingService;
import tech.provve.accounts.service.S3Service;
import tech.provve.api.server.generated.dto.*;
import tech.provve.notification.domain.value.AccountDowngraded;
import tech.provve.notification.domain.value.AccountUpgraded;
import tech.provve.notification.domain.value.RecipientRequisites;
import tech.provve.notification.domain.value.ResetCode;
import tech.provve.notification.service.NotificationSendingService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.logging.Logger;

import static tech.provve.accounts.predicate.StringPredicate.isBlank;
import static tech.provve.accounts.predicate.StringPredicate.isNotBlank;

@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final String JWT_SUBJECT = "sub";

    private static final Logger log = Logger.getLogger(AccountServiceImpl.class.getName());

    private final AccountRepository repository;

    private final JwtIssuingService jwtIssuingService;

    private final JwsParsingService jwsParsingService;

    @External
    private final NotificationSendingService notificationService;

    @External
    private final Vertx vertx;

    private final S3Service s3Service;

    @Override
    public void register(RegisterUserRequest registerUserRequest) {
        validateRegister(registerUserRequest);

        repository.findByLogin(registerUserRequest.getLogin())
                  .ifPresent(_ -> {
                      throw new AccountAlreadyExists("Account with login '%s' is already exists.".formatted(
                              registerUserRequest.getLogin()));
                  });
        repository.findByEmail(registerUserRequest.getEmail())
                  .ifPresent(_ -> {
                      throw new AccountAlreadyExists("Account with email '%s' is already exists.".formatted(
                              registerUserRequest.getEmail()));
                  });

        repository.save(AccountMapper.INSTANCE.map(registerUserRequest));

        log.info("Account " + registerUserRequest.getLogin() + " just registered.");
    }

    private void validateRegister(RegisterUserRequest registerUserRequest) {
        boolean emailSetIllegally = !registerUserRequest.getConsentPersonalData() && isNotBlank(registerUserRequest.getEmail());
        if (emailSetIllegally) {
            throw new DataNotValid("Email cannot be set before an user personal data consent.");
        }
        validateLogin(registerUserRequest.getLogin());
        validatePasswordHash(registerUserRequest.getPasswordHash());
    }

    @Override
    public String authenticate(AuthenticateUserRequest authenticateUserRequest) {
        validateLogin(authenticateUserRequest.getLogin());
        validatePasswordHash(authenticateUserRequest.getPasswordHash());

        Account account = repository.findByLogin(authenticateUserRequest.getLogin())
                                    .orElseThrow(() -> new AccountNotFound(String.format(
                                            "Account with login '%s' not found",
                                            authenticateUserRequest.getLogin()
                                    )));

        boolean invalidPasswordHash = !(account.passwordHash()
                                               .equals(authenticateUserRequest.getPasswordHash()));
        if (invalidPasswordHash) {
            throw new DataNotValid(String.format(
                    "Invalid password hash for '%s' account",
                    authenticateUserRequest.getLogin()
            ));
        }

        return jwtIssuingService.issueAuth(account.login(), account.isPremium());
    }

    private void validateLogin(String login) {
        if (isBlank(login)) {
            throw new DataNotValid("Login must be provided");
        }
    }

    private void validatePasswordHash(String passwordHash) {
        if (isBlank(passwordHash)) {
            throw new DataNotValid("Password hash must be provided");
        }
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
        var jwtPayload = jwsParsingService.parseReset(updatePasswordRequest.getResetToken());
        var login = ((String) jwtPayload.get(JWT_SUBJECT));
        repository.updatePasswordHash(updatePasswordRequest.getNewPasswordHash(), login);
    }

    @Override
    public void updateEmail(UpdateEmailRequest updateEmailRequest) {
        var jwtPayload = jwsParsingService.parseAuth(updateEmailRequest.getAuthToken());
        var login = ((String) jwtPayload.get(JWT_SUBJECT));

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
    public void updateAvatar(UpdateAvatarRequest updateAvatarRequest) {
        var jwtPayload = jwsParsingService.parseAuth(updateAvatarRequest.getAuthToken());
        var login = ((String) jwtPayload.get(JWT_SUBJECT));
        String avatarFile = updateAvatarRequest.getAvatar()
                                               .uploadedFileName();

        vertx.fileSystem()
             .readFile(
                     avatarFile, ar -> {
                         if (ar.failed()) return;

                         String bucket = Config.get("s3.buckets.images");
                         String avatarUrl = s3Service.uploadToS3(
                                 bucket,
                                 ar.result()
                                   .getBytes()
                         );
                         repository.updateAvatarUrl(login, avatarUrl);
                     }
             );
    }

    @Override
    public void upgrade(String login) {
        var account = repository.findByLogin(login)
                                .orElseThrow(() -> new AccountNotFound(String.format(
                                        "Account with login '%s' not found",
                                        login
                                )));
        repository.updatePremium(login, true);

        var future = LocalDateTime.now(ZoneId.of("UTC"))
                                  .plusMonths(1);
        repository.save(new PremiumExpiration(
                login,
                OffsetDateTime.of(future, ZoneOffset.UTC)
        ));

        notificationService.send(new AccountUpgraded(
                new RecipientRequisites(login, account.email())
        ));
    }

    @Override
    public void downgradeAllExpired() {
        repository.findPremiumExpired()
                  .forEach(account -> {
                      notificationService.send(new AccountDowngraded(new RecipientRequisites(
                              account.login(),
                              account.email()
                      )));
                      repository.clearPremiumExpired();
                  });
    }
}
