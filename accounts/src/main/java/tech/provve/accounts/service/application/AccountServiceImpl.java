package tech.provve.accounts.service.application;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.postgresql.util.PSQLException;
import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.mapper.AccountMapper;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

@Log
@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        try {
            validate(registerUserRequest);

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

    private void validate(RegisterUserRequest registerUserRequest) {
        boolean emailSetIllegally = !registerUserRequest.getConsentPersonalData()
                && (registerUserRequest.getEmail() != null
                && !registerUserRequest.getEmail()
                                       .isEmpty());
        if (emailSetIllegally) {
            throw new DataNotValid("Email cannot be set before an user personal data consent.");
        }

        boolean loginEmpty = registerUserRequest.getLogin() == null
                || registerUserRequest.getLogin()
                                      .isEmpty();
        if (loginEmpty) {
            throw new DataNotValid("Login required to be not empty");
        }

        boolean passwordHashEmpty = registerUserRequest.getPasswordHash() == null
                || registerUserRequest.getPasswordHash()
                                      .isEmpty();
        if (passwordHashEmpty) {
            throw new DataNotValid("Password Hash required to be not empty");
        }
    }
}
