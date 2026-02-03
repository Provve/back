package tech.provve.notification.service;

import ch.martinelli.oss.testcontainers.mailpit.MailpitClient;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.TestScope;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import tech.provve.notification.MailIntegrationTest;
import tech.provve.notification.domain.value.AccountUpgraded;
import tech.provve.notification.domain.value.RecipientRequisites;
import tech.provve.notification.domain.value.ResetCode;
import tech.provve.notification.repository.NotificationRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Factory
@TestScope
@InjectTest
class InputNotificationSendingService_Mail_IT extends MailIntegrationTest {

    static final String TARGET_EMAIL = "z@y.z";

    @Inject
    NotificationSendingService notificationSendingService;

    @Bean
    NotificationSendingService notificationSendingService(NotificationRepository repository, Mailer mailer) {
        return new NotificationSendingServiceImpl(repository, mailer);
    }

    @Bean
    Mailer mailer() {
        return MailerBuilder.withSMTPServer(mailpit.getSmtpHost(), mailpit.getSmtpPort())
                            .buildMailer();
    }

    @BeforeEach
    void each() {
        mailpit.getClient()
               .deleteAllMessages();
    }

    @Test
    void send_resetCodeEmail_receiverGetsResetCode() {
        // arrange
        var resetToken = "videman";
        var notifyCommand = new ResetCode(
                new RecipientRequisites("I", TARGET_EMAIL),
                resetToken
        );

        // act
        notificationSendingService.send(notifyCommand);

        // assert
        MailpitClient client = mailpit.getClient();
        var lastMessage = client.getAllMessages()
                                .getFirst();
        assertThat(lastMessage.snippet())
                .contains(resetToken);
    }

    @Test
    void send_emailNull_skipping() {
        // arrange
        var notifyCommand = new AccountUpgraded(
                new RecipientRequisites("I", null)
        );

        // act
        notificationSendingService.send(notifyCommand);

        // assert
        MailpitClient client = mailpit.getClient();
        assertThat(client.getAllMessages()).isEmpty();
    }

}