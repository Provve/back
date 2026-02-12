package tech.provve.accounts.service.application;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.domain.model.value.PremiumExpiration;
import tech.provve.accounts.exception.AccessDenied;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotUnique;
import tech.provve.accounts.exception.NoPersonalDataConsent;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.DeleteAccountRequest;
import tech.provve.api.server.generated.dto.RegisterAccountRequest;
import tech.provve.api.server.generated.dto.UpdateEmailRequest;
import tech.provve.notification.service.NotificationSendingService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@InjectTest
class AccountService_AccountRepository__IT extends PostgresIntegrationTest {

    @Inject
    AccountService service;

    @Inject
    AccountRepository repository;

    @Mock
    NotificationSendingService notificationSendingService;

    @Mock
    JwsParsingService jwsParsingService;


    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    Connection connection() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1");

        try {
            return DriverManager.getConnection(postgres.getJdbcUrl(), props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    void authenticate_invalidPassword_exception() {
        // arrange
        var regRequest = new RegisterAccountRequest(
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
        assertThrows(AccessDenied.class, () -> service.authenticate(authRequest));
    }

    @Test
    void upgrade_paymentConfirmed_accountNowPremium() {
        // arrange
        var account = new Account(
                "b",
                "b@b.b",
                "",
                true,
                "n",
                null,
                false
        );
        repository.save(account);

        // act
        service.upgrade(account.login());

        // assert
        var returned = repository.findByLogin(account.login())
                                 .get();
        assertThat(returned.isPremium())
                .isTrue();
    }

    @Test
    void downgradeAllExpired_someExpired_setPremiumToFalse() {
        // arrange
        var login1 = "c";
        var login2 = "d";

        var premiumAccounts = List.of(
                new Account(
                        login1,
                        "c@c.c",
                        "",
                        true,
                        "n",
                        null,
                        true
                ),
                new Account(
                        login2,
                        "d@d.d",
                        "",
                        true,
                        "n",
                        null,
                        true
                )
        );
        var nowUtc = LocalDateTime.now(ZoneId.of("UTC"));
        var past = nowUtc.minusHours(1);
        var future = OffsetDateTime.of(
                past,
                ZoneOffset.ofHours(7) // сдвиг в будущее
        );
        premiumAccounts.forEach(account -> {
            repository.save(account);
            repository.save(new PremiumExpiration(
                    account.login(),
                    future
            ));
        });

        // act
        service.downgradeAllExpired();

        // assert
        var account1 = repository.findByLogin(login1)
                                 .get();
        var account2 = repository.findByLogin(login2)
                                 .get();
        assertThat(account1.isPremium()).isFalse();
        assertThat(account2.isPremium()).isFalse();
    }

    @Test
    void updateEmail_noPersonalDataConsent_exception() {
        // arrange
        var account = new Account(
                "w",
                "w@w.w",
                "",
                false,
                "n",
                null,
                true
        );
        repository.save(account);

        var authToken = "a";
        UpdateEmailRequest request = new UpdateEmailRequest("new@email.com", authToken);
        when(jwsParsingService.parseAuth(authToken)).thenReturn(Map.of("sub", account.login()));

        // act assert
        assertThrows(NoPersonalDataConsent.class, () -> service.updateEmail(request));
    }

    @Test
    void updateEmail_emailPresentInSystem_exception() {
        // arrange
        var account = new Account(
                "v",
                "v@v.v",
                "",
                true,
                "n",
                null,
                true
        );
        repository.save(account);

        var completelyUnknownEmail = account.email();
        var authToken = "a";
        UpdateEmailRequest request = new UpdateEmailRequest(completelyUnknownEmail, authToken);
        when(jwsParsingService.parseAuth(authToken)).thenReturn(Map.of("sub", account.login()));

        // act assert
        assertThrows(DataNotUnique.class, () -> service.updateEmail(request));
    }

    @Test
    void delete_existingAccount_doesntExistNoMore() {
        // arrange
        var login = "h";
        var account = new Account(
                login,
                "h@h.h",
                "",
                true,
                "n",
                null,
                true
        );
        repository.save(account);

        var request = new DeleteAccountRequest(login);
        when(jwsParsingService.parseAuth(login)).thenReturn(Map.of("sub", account.login()));

        // act
        service.delete(request);

        // assert
        assertThat(repository.findByLogin(login)).isNotPresent();
    }

}
