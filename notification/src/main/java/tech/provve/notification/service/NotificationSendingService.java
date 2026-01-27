package tech.provve.notification.service;

import tech.provve.notification.domain.value.NotifyCommand;

/**
 * Отправляет уведомолеия во внутреннее хранилище и email (если пользователь дал согласие на рассылку).
 */
public interface NotificationSendingService {

    /**
     * @param notifyCommand one of {@link tech.provve.notification.domain.value}
     */
    void send(NotifyCommand notifyCommand);

}
