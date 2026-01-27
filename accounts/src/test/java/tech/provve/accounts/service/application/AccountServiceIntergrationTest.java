package tech.provve.accounts.service.application;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@InjectTest
class AccountServiceIntergrationTest extends PostgresIntegrationTest {

    @Inject
    AccountService service;

    @Test
    void authenticate_nonExistentAccount_exception() {
        // arrange
        var request = new AuthenticateUserRequest(
                "G",
                "1"
        );

        // act assert
        assertThrows(AccountNotFound.class, () -> service.authenticate(request));
    }

    @Test
    void authenticate_invalidPasswordHash_exception() {
        // arrange
        var regRequest = new RegisterUserRequest(
                "a",
                "",
                "1",
                false,
                "u"
        );
        service.register(regRequest);

        var authRequest = new AuthenticateUserRequest(
                "a",
                "2"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.authenticate(authRequest));
    }

}
