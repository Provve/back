package tech.provve.notification.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.notification.db.generated.tables.records.NotificationsRecord;
import tech.provve.notification.domain.entity.InputNotification;
import tech.provve.notification.domain.value.NotificationLevel;

import static tech.provve.api.server.generated.dto.Notification.LevelEnum.*;

@Mapper
public interface NotificationsMapper {

    NotificationsMapper INSTANCE = Mappers.getMapper(NotificationsMapper.class);

    default Notification.LevelEnum level(Short from) {
        return switch (from) {
            case 0 -> ERROR;
            case 1 -> WARNING;
            case 2 -> INFO;
            default -> throw new IllegalStateException("Unknown notification level: " + from);
        };
    }

    default Short level(NotificationLevel from) {
        return switch (from) {
            case ERROR -> 0;
            case WARNING -> 1;
            case INFO -> 2;
        };
    }

    @Mapping(target = "level", source = "level")
    @Mapping(target = "notifiedAccount", source = "receiver")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    NotificationsRecord map(InputNotification from);

}
