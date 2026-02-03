package tech.provve.accounts.service.application;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.postgresql.util.PSQLException;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.domain.model.value.PremiumExpiration;
import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.mapper.AccountMapper;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.accounts.service.JwtIssuingService;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;
import tech.provve.api.server.generated.dto.UpdatePasswordRequest;
import tech.provve.notification.domain.value.AccountDowngraded;
import tech.provve.notification.domain.value.AccountUpgraded;
import tech.provve.notification.domain.value.RecipientRequisites;
import tech.provve.notification.domain.value.ResetCode;
import tech.provve.notification.service.NotificationSendingService;

import java.time.OffsetDateTime;
import java.time.ZoneId;
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

    @Override
    public void register(RegisterUserRequest registerUserRequest) {
        try {
            validateRegister(registerUserRequest);

            repository.save(AccountMapper.INSTANCE.map(registerUserRequest));

            log.info("Account " + registerUserRequest.getLogin() + " just registered.");
        } catch (IntegrityConstraintViolationException e) {
            var psqlException = ((PSQLException) e.getCause());
            var error = psqlException.getServerErrorMessage();
            boolean loginColumnError = error != null
                    && error.getDetail() != null
                    && error.getDetail()
                            .contains("login");
            if (loginColumnError) {
                throw new AccountAlreadyExists(String.format(
                        "Account with login '%s' is already exists.",
                        registerUserRequest.getLogin()
                ));
            }
        }
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
        var jwtPayload = jwsParsingService.parseReset(updatePasswordRequest.getResetCode());
        var login = ((String) jwtPayload.get(JWT_SUBJECT));
        repository.updatePasswordHash(updatePasswordRequest.getNewPasswordHash(), login);
    }

    @Override
    public void upgrade(String login) {
        var account = repository.findByLogin(login)
                                .orElseThrow(() -> new AccountNotFound(String.format(
                                        "Account with login '%s' not found",
                                        login
                                )));
        repository.updatePremium(login, true);
        repository.save(new PremiumExpiration(login, OffsetDateTime.now(ZoneId.of("UTC"))));

        notificationService.send(new AccountUpgraded(
                new RecipientRequisites(login, account.email())
        ));
    }

    @Override
    public void downgradeAllExpired() {
        repository.findPremiumExpired()
                  .forEach(account ->
                                   notificationService.send(new AccountDowngraded(new RecipientRequisites(
                                           account.login(),
                                           account.email()
                                   ))));
    }
}
