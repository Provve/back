package tech.provve.notification.service;

import io.avaje.config.Config;
import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import tech.provve.notification.domain.entity.InputNotification;
import tech.provve.notification.domain.value.NotifyCommand;
import tech.provve.notification.repository.NotificationRepository;

import java.util.Objects;

import static tech.provve.notification.domain.value.NotifyCommand.Address.EMAIL;
import static tech.provve.notification.domain.value.NotifyCommand.Address.INTERNAL;

/**
 * Infrastructure service for sending mails.
 */
@Singleton
@RequiredArgsConstructor
public class NotificationSendingServiceImpl implements NotificationSendingService {

    private static final String TEMPLATES_PATH = "templates/";

    private final NotificationRepository repository;

    @External
    private final Mailer mailer;

    private final String mailServerUsername = Config.get("mail.username");

    @Override
    public void send(NotifyCommand notifyCommand) {
        var notificationTemplate = loadTemplateFor(notifyCommand);
        var notification = notifyCommand.fillTemplate(notificationTemplate);

        var addresses = notifyCommand.addresses();
        var receipeeEmail = notifyCommand.requisites()
                                         .email();
        if (addresses.contains(EMAIL)) {
            var email = EmailBuilder.startingBlank()
                                    .from(mailServerUsername)
                                    .to(receipeeEmail)
                                    .withHTMLText(notification)
                                    .buildEmail();
            mailer.sendMail(email);
        }
        if (addresses.contains(INTERNAL)) {
            repository.save(
                    new InputNotification(
                            receipeeEmail,
                            notifyCommand.level(),
                            notification
                    )
            );
        }
    }

    @SneakyThrows
    public String loadTemplateFor(NotifyCommand notifyCommand) {
        try (var templateStream = getClass().getClassLoader()
                                            .getResourceAsStream(TEMPLATES_PATH + notifyCommand.templateName())) {
            return new String(Objects.requireNonNull(templateStream)
                                     .readAllBytes());
        }
    }
}
