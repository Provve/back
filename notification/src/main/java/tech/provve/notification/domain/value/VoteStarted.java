package tech.provve.notification.domain.value;

import java.util.List;

public record VoteStarted(RecipientRequisites requisites, String voteName) implements NotifyCommand {

    @Override
    public String subject() {
        return "Запущено голосование";
    }

    @Override
    public NotificationLevel level() {
        return NotificationLevel.INFO;
    }

    @Override
    public String templateName() {
        return "vote_started.html";
    }

    @Override
    public String fillTemplate(String rawTemplate) {
        return rawTemplate.replace("{{vote}}", voteName);
    }

    @Override
    public List<Address> addresses() {
        return List.of(Address.values());
    }
}
