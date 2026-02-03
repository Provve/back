package tech.provve.payment.service.application;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.exception.AccountAlreadyUpgraded;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.payment.PostgresIntegrationTest;
import tech.provve.payment.domain.value.Invoice;
import tech.provve.payment.repository.RobokassaInvoiceRepository;

import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@InjectTest
class PaymentService_AccountRepository__IT extends PostgresIntegrationTest {

    @Inject
    PaymentService paymentService;

    @Inject
    AccountRepository accountRepository;

    @Inject
    RobokassaInvoiceRepository invoiceRepository;

    @Mock
    HttpClient httpClient;

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
    void createInvoice_unexistentAccount_exception() {
        // arrange
        var login = "q";

        // assert
        assertThatThrownBy(() -> paymentService.createInvoice(login))
                .isExactlyInstanceOf(AccountNotFound.class);
    }

    @Test
    void createInvoice_accountAlreadyBoughtPremium_exception() {
        // arrange
        var login = "whitelight";
        boolean premium = true;

        // act
        registerAccount(login, premium);

        // assert
        assertThatThrownBy(() -> paymentService.createInvoice(login))
                .isExactlyInstanceOf(AccountAlreadyUpgraded.class);
    }

    @Test
    void confirmPayment_signaturesDoesntMatch_false() {
        // arrange
        var login = "a";
        boolean premium = true;
        registerAccount(login, premium);

        var generatedSignature = "1";
        var receivedSignature = "12";
        var invoice = new Invoice(login, generatedSignature);
        invoiceRepository.save(invoice);

        // act
        boolean confirmation = paymentService.confirmPayment(login, receivedSignature);

        // assert
        assertThat(confirmation).isFalse();
    }

    @Test
    void confirmPayment_signaturesDoesMatch_true() {
        // arrange
        var login = "b";
        boolean premium = true;
        registerAccount(login, premium);

        var generatedSignature = "1";
        var receivedSignature = "1";
        var invoice = new Invoice(login, generatedSignature);
        invoiceRepository.save(invoice);

        // act
        boolean confirmation = paymentService.confirmPayment(login, receivedSignature);

        // assert
        assertThat(confirmation).isTrue();
    }

    private void registerAccount(String login, boolean premium) {
        var account = new Account(
                login,
                "b@c.d",
                "",
                true,
                "n",
                null,
                premium
        );

        accountRepository.save(account);
    }

}