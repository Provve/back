package tech.provve.accounts.service.application;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterAccountRequest;
import tech.provve.api.server.generated.dto.UpdatePersonalDataConsentRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@InjectTest
class AccountServiceTest {

    @Inject
    AccountService service;

    @Mock
    AccountRepository accountRepository;

    @Mock
    JwsParsingService jwsParsingService;

    @Test
    void register_nonEmptyEmail_falsePersonalDataConsent_exception() {
        // arrange
        var request = new RegisterAccountRequest(
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
        var request = new RegisterAccountRequest(
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
        var request = new RegisterAccountRequest(
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

    @Test
    void updatePersonalDataConsent_truePdConsent_saved() {
        // arrange
        var login = "w";
        var authToken = "a";
        var request = new UpdatePersonalDataConsentRequest(true, authToken);
        when(jwsParsingService.parseAuth(authToken)).thenReturn(Map.of("sub", login));

        // act
        service.updatePersonalDataConsent(request);

        // assert
        verify(accountRepository).updatePersonalDataConsent(login, true);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void updatePersonalDataConsent_falsePdConsent_removedEmail_savedPdConsent() {
        // arrange
        var login = "w";
        var authToken = "a";
        var request = new UpdatePersonalDataConsentRequest(false, authToken);
        when(jwsParsingService.parseAuth(authToken)).thenReturn(Map.of("sub", login));

        // act
        service.updatePersonalDataConsent(request);

        // assert
        verify(accountRepository).updatePersonalDataConsent(login, false);
        verify(accountRepository).updateEmail(login, null);
    }

}