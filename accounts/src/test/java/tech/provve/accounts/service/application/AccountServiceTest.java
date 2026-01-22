package tech.provve.accounts.service.application;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@InjectTest
class AccountServiceTest {

    @Inject
    AccountService service;

    @Test
    void register_nonEmptyEmail_falsePersonalDataConsent_exception() {
        // arrange
        var request = new RegisterUserRequest(
                "a",
                "b",
                "h",
                false,
                "u"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.register(request));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void register_emptyLogin_exception(String login) {
        // arrange
        var request = new RegisterUserRequest(
                login,
                "",
                "h",
                false,
                "u"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.register(request));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void register_emptyPasswordHash_exception(String passwordHash) {
        // arrange
        var request = new RegisterUserRequest(
                "a",
                "",
                passwordHash,
                false,
                "u"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.register(request));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void authenticate_emptyLogin_exception(String login) {
        // arrange
        var request = new AuthenticateUserRequest(
                login,
                "1"
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.authenticate(request));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void authenticate_emptyPasswordHash_exception(String passwordHash) {
        // arrange
        var request = new AuthenticateUserRequest(
                "a",
                passwordHash
        );

        // act assert
        assertThrows(DataNotValid.class, () -> service.authenticate(request));
    }

}