package tech.provve.notification;

import alekseyvideman.dop.Collection;
import io.avaje.config.Config;
import lombok.SneakyThrows;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import tech.provve.api.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.generated.dto.Cursor;
import tech.provve.api.generated.dto.Notification;
import tech.provve.api.generated.dto.Notifications;
import tech.provve.constants.Entity;
import tech.provve.notification.domain.value.NotificationLevel;
import tech.provve.util.Jackson;
import tech.provve.util.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Отправляет уведомолеия во внутреннее хранилище и email (если пользователь дал согласие на рассылку).
 */
public class NotificationSending {

    public enum Address {
        EMAIL,
        INTERNAL
    }


    private static final String TEMPLATES_PATH = "templates/";

    private static final String GENERAL_TEMPLATE = "email-general.html";


    private static final Mailer MAILER = mailer();

    /**
     * @param notifyCommand a map with keys defined in {@link Entity.NotifyCommand}
     * @param notification  rendered notification content
     */
    public static void send(Map<String, Object> notifyCommand, String notification) {
        var mailServerUsername = Objects.requireNonNull(MAILER.getServerConfig())
                                        .getUsername();
        Map<String, Object> requisites = Collection.get(notifyCommand, Entity.NotifyCommand.REQUISITES);
        String receipeeEmail = Collection.getOrNull(requisites, Entity.RecipientRequisites.EMAIL);
        List<Address> addresses = Collection.get(notifyCommand, Entity.NotifyCommand.ADDRESSES);
        boolean canSkipProcessing = receipeeEmail == null
                && addresses.size() == 1
                && addresses.contains(Address.EMAIL);
        if (canSkipProcessing) {
            return;
        }

        var generalTemplate = loadTemplate(GENERAL_TEMPLATE);
        var finalNotification = generalTemplate.replace("{{content}}", notification);

        if (addresses.contains(Address.EMAIL) && receipeeEmail != null) {
            var email = EmailBuilder.startingBlank()
                                    .from(mailServerUsername)
                                    .to(receipeeEmail)
                                    .withHTMLText(finalNotification)
                                    .buildEmail();
            MAILER.sendMail(email);
        }
        if (addresses.contains(Address.INTERNAL)) {
            Map<String, Object> inputNotification = new HashMap<>();
            inputNotification.put(Entity.Notification.RECEIVER, receipeeEmail);
            inputNotification.put(Entity.Notification.LEVEL, Collection.get(notifyCommand, Entity.NotifyCommand.LEVEL));
            inputNotification.put(Entity.Notification.MESSAGE, notification);
            Storage.saveNotification(inputNotification);
        }
    }

    public static Notifications list(String login, CollectionAuthenticatedRequest request) {
        List<Notification> all = Storage.findAllNotificationsBy(login, request.getPagination()
                                                                              .getPrevious(),
                                                                request.getPagination()
                                                                       .getSize())
                                        .stream()
                                        .map(it -> Jackson.convertToClass(it, Notification.class))
                                        .toList();
        if (all.isEmpty()) {
            return new Notifications(all, new Cursor(""));
        }
        ;
        var cursor = new Cursor(String.valueOf(all.getLast()
                                                  .getId()));
        return new Notifications(all, cursor);
    }

    @SneakyThrows
    public static String loadTemplate(String templateName) {
        try (var templateStream = NotificationSending.class.getClassLoader()
                                                           .getResourceAsStream(TEMPLATES_PATH + templateName)) {
            return new String(Objects.requireNonNull(
                                             templateStream,
                                             "Template " + templateName + " not found"
                                     )
                                     .readAllBytes());
        }
    }

    private static Mailer mailer() {
        var host = Config.get("mail.host");
        int port = Config.getInt("mail.port");
        var username = Config.get("mail.username");
        var password = Config.get("mail.password");

        return MailerBuilder
                .withSMTPServer(host, port, username, password)
                .buildMailer();
    }

    public static String fillAuthoredSkillSavedTemplate(String login, String skillName) {
        return loadTemplate("authored_skill_saved.html")
                .replace("{{login}}", login)
                .replace("{{skill}}", skillName);
    }

    public static String fillAuthoredSkillNotSavedTemplate(String login, String skillName) {
        return loadTemplate("authored_skill_not_saved.html")
                .replace("{{login}}", login)
                .replace("{{skill}}", skillName);
    }

    public static String fillResetCodeTemplate(String login, String resetToken) {
        return loadTemplate("reset-code.html")
                .replace("{{login}}", login)
                .replace("{{reset_token}}", resetToken);
    }

    public static String fillAccountUpgradedTemplate(String login) {
        return loadTemplate("account_upgraded.html")
                .replace("{{login}}", login);
    }

    public static String fillAccountDowngradedTemplate(String login) {
        return loadTemplate("account_downgraded.html")
                .replace("{{login}}", login);
    }

    public static String fillVoteStartedTemplate(String voteName) {
        return loadTemplate("vote_started.html")
                .replace("{{vote}}", voteName);
    }

    public static Map<String, Object> authoredSkillSavedCommand(String login, String email) {
        Map<String, Object> command = new HashMap<>();
        command.put(Entity.NotifyCommand.SUBJECT, "Навык сохранен");
        command.put(Entity.NotifyCommand.LEVEL, NotificationLevel.INFO);
        command.put(Entity.NotifyCommand.REQUISITES, requisites(login, email));
        command.put(Entity.NotifyCommand.ADDRESSES, List.of(Address.EMAIL, Address.INTERNAL));
        return command;
    }

    public static Map<String, Object> authoredSkillNotSavedCommand(String login, String email) {
        Map<String, Object> command = new HashMap<>();
        command.put(Entity.NotifyCommand.SUBJECT, "Навык не сохранен");
        command.put(Entity.NotifyCommand.LEVEL, NotificationLevel.ERROR);
        command.put(Entity.NotifyCommand.REQUISITES, requisites(login, email));
        command.put(Entity.NotifyCommand.ADDRESSES, List.of(Address.EMAIL, Address.INTERNAL));
        return command;
    }

    public static Map<String, Object> requisites(String login, String email) {
        Map<String, Object> requisites = new HashMap<>();
        requisites.put(Entity.RecipientRequisites.LOGIN, login);
        requisites.put(Entity.RecipientRequisites.EMAIL, email);
        return requisites;
    }

    public static Map<String, Object> resetCodeCommand(String login, String email) {
        Map<String, Object> command = new HashMap<>();
        command.put(Entity.NotifyCommand.SUBJECT, "Сброс пароля");
        command.put(Entity.NotifyCommand.LEVEL, NotificationLevel.INFO);
        command.put(Entity.NotifyCommand.REQUISITES, requisites(login, email));
        command.put(Entity.NotifyCommand.ADDRESSES, List.of(Address.EMAIL));
        return command;
    }

    public static Map<String, Object> accountUpgradedCommand(String login, String email) {
        Map<String, Object> command = new HashMap<>();
        command.put(Entity.NotifyCommand.SUBJECT, "Уровень аккаунта повышен!");
        command.put(Entity.NotifyCommand.LEVEL, NotificationLevel.INFO);
        command.put(Entity.NotifyCommand.REQUISITES, requisites(login, email));
        command.put(Entity.NotifyCommand.ADDRESSES, List.of(Address.EMAIL, Address.INTERNAL));
        return command;
    }

    public static Map<String, Object> accountDowngradedCommand(String login, String email) {
        Map<String, Object> command = new HashMap<>();
        command.put(Entity.NotifyCommand.SUBJECT, "Премиум-подписка кончилась");
        command.put(Entity.NotifyCommand.LEVEL, NotificationLevel.WARNING);
        command.put(Entity.NotifyCommand.REQUISITES, requisites(login, email));
        command.put(Entity.NotifyCommand.ADDRESSES, List.of(Address.EMAIL, Address.INTERNAL));
        return command;
    }

    public static Map<String, Object> voteStartedCommand(String login, String email) {
        Map<String, Object> command = new HashMap<>();
        command.put(Entity.NotifyCommand.SUBJECT, "Запущено голосование");
        command.put(Entity.NotifyCommand.LEVEL, NotificationLevel.INFO);
        command.put(Entity.NotifyCommand.REQUISITES, requisites(login, email));
        command.put(Entity.NotifyCommand.ADDRESSES, List.of(Address.EMAIL, Address.INTERNAL));
        return command;
    }
}
