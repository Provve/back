package tech.provve.accounts.service.application;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;
import tech.provve.notification.service.NotificationSendingService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
        b.bean(Connection.class, connection());
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
        Assertions.assertThat(returned.isPremium())
                  .isTrue();
    }

}
