package tech.provve.notification.domain.value;

import java.util.List;

public record AccountUpgraded(RecipientRequisites requisites) implements NotifyCommand {

    @Override
    public String subject() {
        return "Уровень аккаунта повышен!";
    }

    @Override
    public NotificationLevel level() {
        return NotificationLevel.INFO;
    }

    @Override
    public String templateName() {
        return "account_upgraded.html";
    }

    @Override
    public String fillTemplate(String rawTemplate) {
        return rawTemplate.replace("{{login}}", requisites().login());
    }

    @Override
    public List<Address> addresses() {
        return List.of(Address.values());
    }
}
