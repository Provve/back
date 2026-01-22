package tech.provve.accounts.service.application;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.postgresql.util.PSQLException;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.mapper.AccountMapper;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwtIssuingService;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

import static tech.provve.accounts.predicate.StringPredicate.isBlank;
import static tech.provve.accounts.predicate.StringPredicate.isNotBlank;

@Log
@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final JwtIssuingService jwtIssuingService;

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

        return jwtIssuingService.issue(account.login(), account.isPremium());
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
}
