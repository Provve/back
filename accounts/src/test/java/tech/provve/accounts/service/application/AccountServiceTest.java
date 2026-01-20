package tech.provve.accounts.service.application;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@InjectTest
class AccountServiceTest {

    @Inject
    AccountService service;

    @Test
    void registerUser_nonEmptyEmail_falsePersonalDataConsent_exception() {
        // arrange
        var registerRequest = new RegisterUserRequest(
                "a",
                "b",
                "h",
                false,
                "u"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.registerUser(registerRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void registerUser_emptyLogin_exception(String login) {
        // arrange
        var registerRequest = new RegisterUserRequest(
                login,
                "",
                "h",
                false,
                "u"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.registerUser(registerRequest));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void registerUser_emptyPasswordHash_exception(String passwordHash) {
        // arrange
        var registerRequest = new RegisterUserRequest(
                "a",
                "",
                passwordHash,
                false,
                "u"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.registerUser(registerRequest));
    }

}