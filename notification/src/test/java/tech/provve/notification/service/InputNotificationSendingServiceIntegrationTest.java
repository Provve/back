package tech.provve.notification.service;

import ch.martinelli.oss.testcontainers.mailpit.MailpitClient;
import ch.martinelli.oss.testcontainers.mailpit.MailpitContainer;
import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tech.provve.notification.domain.value.RecipientRequisites;
import tech.provve.notification.domain.value.ResetCode;

@InjectTest
@Testcontainers
class InputNotificationSendingServiceIntegrationTest {

    @Container
    static MailpitContainer mailpit = new MailpitContainer();

    Mailer mailer = MailerBuilder.withSMTPServer(mailpit.getSmtpHost(), mailpit.getSmtpPort())
                                 .buildMailer();

    static final String TARGET_EMAIL = "z@y.z";

    @Setup
    void setup(BeanScopeBuilder builder) {
        builder.bean(Mailer.class, mailer);
    }

    @Inject
    NotificationSendingService notificationSendingService;

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
        Assertions.assertThat(lastMessage.snippet())
                  .contains(resetToken);
    }

}