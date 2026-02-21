package tech.provve.api.server.controller;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.generated.dto.*;
import tech.provve.api.server.validation.Constraints;

import static org.assertj.core.api.Assertions.assertThat;

@InjectTest
class AccountsControllerTest {

    static final String INCORRECT_EMAILS = """
            a@.com.com,
            @.com,
            a
            """;

    @Inject
    AccountsController controller;

    @Mock
    AccountService accountService;

    @Test
    void registerAccount_nonEmptyEmail_falsePersonalDataConsent_exception() {
        // arrange
        var request = new RegisterAccountRequest(
                "a",
                "b",
                "h",
                false,
                "u"
        );

        // act
        var result = controller.registerAccount(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void registerAccount_emptyLogin_exception(String login) {
        // arrange
        var request = new RegisterAccountRequest(
                login,
                "",
                "h",
                false,
                "u"
        );

        // act
        var result = controller.registerAccount(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void registerAccount_emptyPassword_exception(String passwordHash) {
        // arrange
        var request = new RegisterAccountRequest(
                "a",
                "",
                passwordHash,
                false,
                "u"
        );

        // act
        var result = controller.registerAccount(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void authenticateUser_emptyLogin_exception(String login) {
        // arrange
        var request = new AuthenticateUserRequest(
                login,
                "1"
        );

        // act
        var result = controller.authenticateUser(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void authenticateUser_emptyPassword_exception(String passwordHash) {
        // arrange
        var request = new AuthenticateUserRequest(
                "a",
                passwordHash
        );

        // act
        var result = controller.authenticateUser(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @Test
    void authenticateUser_loginTooLong_exception() {
        // arrange
        var request = new AuthenticateUserRequest(
                "a".repeat(Constraints.LOGIN_MAX_LENGTH + 1),
                "1"
        );

        // act
        var result = controller.authenticateUser(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void deleteAccount_emptyAuthToken_exception(String authToken) {
        // arrange
        var request = new DeleteAccountRequest(authToken);

        // act
        var result = controller.deleteAccount(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void updateAvatar_emptyAuthToken_exception(String authToken) {
        // arrange
        var request = new UpdateAvatarRequest(null, authToken);

        // act
        var result = controller.updateAvatar(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void updateEmail_emptyAuthToken_exception(String authToken) {
        // arrange
        var email = "a@b.c";
        var request = new UpdateEmailRequest(email, authToken);

        // act
        var result = controller.updateEmail(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void updateEmail_emptyEmail_exception(String email) {
        // arrange
        var request = new UpdateEmailRequest(email, "1");

        // act
        var result = controller.updateEmail(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @CsvSource(INCORRECT_EMAILS)
    void updateEmail_incorrectEmail_exception(String email) {
        // arrange
        var request = new UpdateEmailRequest(email, "1");

        // act
        var result = controller.updateEmail(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void updatePassword_emptyPassword_exception(String password) {
        // arrange
        var request = new UpdatePasswordRequest("1", password);

        // act
        var result = controller.updatePassword(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void updatePassword_emptyResetToken_exception(String resetToken) {
        // arrange
        var request = new UpdatePasswordRequest(resetToken, "1");

        // act
        var result = controller.updatePassword(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @Test
    void updatePersonalDataConsent_nullConsent_exception() {
        // arrange
        var request = new UpdatePersonalDataConsentRequest(null, "1");

        // act
        var result = controller.updatePersonalDataConsent(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void updatePersonalDataConsent_emptyAuthToken_exception(String authToken) {
        // arrange
        var request = new UpdatePersonalDataConsentRequest(true, authToken);

        // act
        var result = controller.updatePersonalDataConsent(request);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }

    @ParameterizedTest
    @CsvSource(INCORRECT_EMAILS)
    void requestResetCode_incorrectEmail_exception(String email) {
        // arrange act
        var result = controller.requestResetCode(email);

        // assert
        assertThat(result.failed()).isTrue();
        assertThat(result.cause()).isExactlyInstanceOf(HttpException.class);
    }
}