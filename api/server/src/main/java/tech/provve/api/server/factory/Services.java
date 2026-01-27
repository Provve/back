package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.vertx.ext.auth.jwt.JWTAuth;
import jakarta.inject.Named;
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

import java.sql.Connection;

@Factory
public class Services {

    @Bean
    public AccountService accountService(AccountRepository repository,
                                         JwtIssuingService jwtIssuingService,
                                         JwsParsingService jwsParsingService,
                                         NotificationSendingService notificationSendingService) {
        return new AccountServiceImpl(repository, jwtIssuingService, jwsParsingService, notificationSendingService);
    }

    @Bean
    public AccountRepository accountRepository(Connection connection) {
        return new AccountRepository(connection);
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
    public NotificationRepository notificationRepository(Connection connection) {
        return new NotificationRepository(connection);
    }
}
