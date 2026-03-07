package tech.provve.notification.domain.value;

import java.util.List;

public record AuthoredExamSaved(RecipientRequisites requisites, String examName) implements NotifyCommand {

    @Override
    public String subject() {
        return "Экзамен сохранен";
    }

    @Override
    public NotificationLevel level() {
        return NotificationLevel.INFO;
    }

    @Override
    public String templateName() {
        return "authored_exam_saved.html";
    }

    @Override
    public String fillTemplate(String rawTemplate) {
        return rawTemplate.replace("{{login}}", requisites().login())
                          .replace("{{exam}}", examName);
    }

    @Override
    public List<Address> addresses() {
        return List.of(Address.values());
    }
}
