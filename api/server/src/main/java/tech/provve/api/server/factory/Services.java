package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import jakarta.inject.Named;
import org.jooq.DSLContext;
import org.simplejavamail.api.mailer.Mailer;
import software.amazon.awssdk.services.s3.S3Client;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.*;
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
    public S3Service s3Service(S3Client s3Client) {
        return new S3Service(s3Client);
    }

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
                                         NotificationSendingService notificationSendingService,
                                         Vertx vertx,
                                         S3Service s3Service) {
        return new AccountServiceImpl(
                repository,
                jwtIssuingService,
                jwsParsingService,
                notificationSendingService,
                vertx,
                s3Service
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
