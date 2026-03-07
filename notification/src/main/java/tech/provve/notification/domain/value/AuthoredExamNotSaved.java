package tech.provve.notification.domain.value;

import java.util.List;

public record AuthoredExamNotSaved(RecipientRequisites requisites, String examName) implements NotifyCommand {

    @Override
    public String subject() {
        return "Экзамен не сохранен";
    }

    @Override
    public NotificationLevel level() {
        return NotificationLevel.ERROR;
    }

    @Override
    public String templateName() {
        return "authored_exam_not_saved.html";
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
