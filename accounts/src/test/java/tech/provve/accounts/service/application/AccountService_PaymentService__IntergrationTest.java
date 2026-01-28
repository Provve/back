package tech.provve.accounts.service.application;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.notification.service.NotificationSendingService;
import tech.provve.payment.service.application.PaymentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.mockito.Mockito.when;

@InjectTest
class AccountService_PaymentService__IntergrationTest extends PostgresIntegrationTest {

    @Inject
    AccountService service;

    @Inject
    AccountRepository repository;

    @Inject
    PaymentService paymentService;

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
    void upgrade_gotMoney_accountNowPremium() {
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
        var returned = repository.findByLogin(account.login())
                                 .get();

        // act
        service.upgrade(returned.login());

        // assert
        Assertions.assertNotNull(repository);
    }

}
