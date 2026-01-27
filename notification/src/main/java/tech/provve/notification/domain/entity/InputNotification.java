package tech.provve.notification.domain.entity;

import tech.provve.notification.domain.value.NotificationLevel;

public record InputNotification(String receiver, NotificationLevel level, String message) {

}
