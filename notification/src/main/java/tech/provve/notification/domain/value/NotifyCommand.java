package tech.provve.notification.domain.value;

import java.util.List;

/**
 * Команда на построение уведомления
 */
public interface NotifyCommand {

    String subject();

    NotificationLevel level();

    RecipientRequisites requisites();

    /**
     * Get template name to be loaded and parsed.
     *
     * @return full file name
     */
    String templateName();

    /**
     * Fill specific values to placeholders. Placeholders are in {{name}} format.
     *
     * @param rawTemplate without values
     * @return final message with all values set
     */
    String fillTemplate(String rawTemplate);

    /**
     * Узнать доступные направления отправки уведомления.
     */
    List<Address> addresses();

    enum Address {
        /**
         * Отправить по почте.
         */
        EMAIL,

        /**
         * Внутреннее хранилище.
         */
        INTERNAL
    }
}
