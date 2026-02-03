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
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;
import tech.provve.notification.service.NotificationSendingService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InjectTest
class AccountService_AccountRepository__IT extends PostgresIntegrationTest {

    @Inject
    AccountService service;

    @Inject
    AccountRepository repository;

    @Mock
    NotificationSendingService notificationSendingService;

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

    @Test
    void upgrade_paymentConfirmed_accountNowPremium() {
        // arrange
        var account = new Account(
                "b",
                "b@c.d",
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
                        "b@c.d",
                        "",
                        true,
                        "n",
                        null,
                        true
                ),
                new Account(
                        login2,
                        "b@c.d",
                        "",
                        true,
                        "n",
                        null,
                        true
                )
        );
        premiumAccounts.forEach(account -> {
            repository.save(account);
            var past = LocalDateTime.now(ZoneId.of("UTC"))
                                    .minusHours(1);
            repository.save(new PremiumExpiration(
                    account.login(),
                    OffsetDateTime.of(past, ZoneOffset.ofHours(7)) // сдвиг в будущее
            ));
        });

        // act
        List<Account> premiumExpiredAccounts = repository.findPremiumExpired();

        // assert
        assertThat(premiumExpiredAccounts).extracting(Account::login)
                                          .containsExactly(login1, login2);
        assertThat(premiumExpiredAccounts).extracting(Account::isPremium)
                                          .allMatch(premium -> false == premium);
    }

}
