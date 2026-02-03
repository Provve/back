package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import io.vertx.ext.auth.jwt.JWTAuth;
import jakarta.inject.Named;
import org.jooq.DSLContext;
import org.simplejavamail.api.mailer.Mailer;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.accounts.service.JwsParsingServiceImpl;
import tech.provve.accounts.service.JwtIssuingService;
import tech.provve.accounts.service.JwtIssuingServiceImpl;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.accounts.service.application.AccountServiceImpl;
import tech.provve.notification.repository.NotificationRepository;
import tech.provve.notification.service.NotificationSendingService;
import tech.provve.notification.service.NotificationSendingServiceImpl;
import tech.provve.payment.gateway.robokassa.ApiClient;
import tech.provve.payment.repository.RobokassaInvoiceRepository;
import tech.provve.payment.service.application.PaymentService;
import tech.provve.payment.service.application.PaymentServiceImpl;

import java.net.URI;
import java.net.http.HttpRequest;

import static tech.provve.payment.factory.HttpClientFactory.GET_PAYMENT_LINK_URL;

@Factory
public class Services {

    @Bean
    public RobokassaInvoiceRepository robokassaInvoiceRepository(@External DSLContext dsl) {
        return new RobokassaInvoiceRepository(dsl);
    }

    @Bean
    public HttpRequest.Builder getPaymentLinkBuilder() {
        return HttpRequest.newBuilder()
                          .uri(URI.create(GET_PAYMENT_LINK_URL));
    }

    @Bean
    public ApiClient apiClient(@External HttpRequest.Builder getPaymentLinkBuilder) {
        return new ApiClient(getPaymentLinkBuilder);
    }

    @Bean
    public PaymentService paymentService(ApiClient apiClient, @External RobokassaInvoiceRepository invoiceRepository, @External AccountRepository accountRepository) {
        return new PaymentServiceImpl(apiClient, invoiceRepository, accountRepository);
    }

    @Bean
    public AccountService accountService(AccountRepository repository,
                                         JwtIssuingService jwtIssuingService,
                                         JwsParsingService jwsParsingService,
                                         NotificationSendingService notificationSendingService) {
        return new AccountServiceImpl(
                repository,
                jwtIssuingService,
                jwsParsingService,
                notificationSendingService
        );
    }

    @Bean
    public AccountRepository accountRepository(DSLContext dsl) {
        return new AccountRepository(dsl);
    }

    @Bean
    public JwtIssuingService jwtIssuingService(@Named("auth") JWTAuth jwtAuth,
                                               @Named("reset") JWTAuth jwtReset) {
        return new JwtIssuingServiceImpl(jwtAuth, jwtReset);
    }

    @Bean
    public JwsParsingService jwsParsingService() {
        return new JwsParsingServiceImpl();
    }

    @Bean
    public NotificationSendingService notificationSendingService(NotificationRepository notificationRepository, Mailer mailer) {
        return new NotificationSendingServiceImpl(notificationRepository, mailer);
    }

    @Bean
    public NotificationRepository notificationRepository(DSLContext dsl) {
        return new NotificationRepository(dsl);
    }
}
