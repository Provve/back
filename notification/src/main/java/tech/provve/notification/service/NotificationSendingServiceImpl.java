package tech.provve.notification.service;

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

    private static final String GENERAL_TEMPLATE = "email-general.html";

    private final NotificationRepository repository;

    @External
    private final Mailer mailer;

    @Override
    public void send(NotifyCommand notifyCommand) {
        var mailServerUsername = mailer.getServerConfig()
                                       .getUsername();
        var receipeeEmail = notifyCommand.requisites()
                                         .email();
        boolean canSkipProcessing = receipeeEmail == null
                && notifyCommand.addresses()
                                .size() == 1
                && notifyCommand.addresses()
                                .contains(EMAIL);
        if (canSkipProcessing) {
            return;
        }

        var notificationTemplate = loadTemplate(notifyCommand.templateName());
        var notification = notifyCommand.fillTemplate(notificationTemplate);
        var generalTemplate = loadTemplate(GENERAL_TEMPLATE);
        var finalNotification = generalTemplate.replace("{{content}}", notification);

        var addresses = notifyCommand.addresses();
        if (addresses.contains(EMAIL) && receipeeEmail != null) {
            var email = EmailBuilder.startingBlank()
                                    .from(mailServerUsername)
                                    .to(receipeeEmail)
                                    .withHTMLText(finalNotification)
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
    public String loadTemplate(String templateName) {
        try (var templateStream = getClass().getClassLoader()
                                            .getResourceAsStream(TEMPLATES_PATH + templateName)) {
            return new String(Objects.requireNonNull(
                                             templateStream,
                                             "Template " + templateName + " not found"
                                     )
                                     .readAllBytes());
        }
    }
}
