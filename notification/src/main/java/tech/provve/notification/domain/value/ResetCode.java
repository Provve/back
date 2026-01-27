package tech.provve.notification.domain.value;

import java.util.List;

public record ResetCode(RecipientRequisites requisites, String resetToken) implements NotifyCommand {

    @Override
    public String subject() {
        return "Сброс пароля";
    }

    @Override
    public NotificationLevel level() {
        return NotificationLevel.INFO;
    }

    @Override
    public String templateName() {
        return "reset-code.html";
    }

    @Override
    public String fillTemplate(String rawTemplate) {
        return rawTemplate
                .replace("{{login}}", requisites().login())
                .replace("{{reset_token}}", resetToken);
    }

    @Override
    public List<Address> addresses() {
        return List.of(Address.EMAIL);
    }
}

