package tech.provve.notification.domain.value;

import java.util.List;

public record AccountDowngraded(RecipientRequisites requisites) implements NotifyCommand {

    @Override
    public String subject() {
        return "Премиум-подписка кончилась.";
    }

    @Override
    public NotificationLevel level() {
        return NotificationLevel.WARNING;
    }

    @Override
    public String templateName() {
        return "account_downgraded.html";
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
