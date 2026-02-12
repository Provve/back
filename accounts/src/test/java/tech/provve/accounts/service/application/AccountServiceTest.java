package tech.provve.accounts.service.application;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.UpdatePersonalDataConsentRequest;

import java.util.Map;

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