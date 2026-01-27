package tech.provve.notification.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.notification.domain.entity.InputNotification;
import tech.provve.notification.mapper.NotificationsMapper;

import java.sql.Connection;
import java.util.List;

import static tech.provve.notification.db.generated.tables.Notifications.NOTIFICATIONS_;

@Singleton
@RequiredArgsConstructor
public class NotificationRepository {

    @External
    private final Connection connection;

    private final RecordMapper<Record, Notification> outputMapper = result ->
            new Notification(
                    result.get(NOTIFICATIONS_.ID),
                    NotificationsMapper.INSTANCE.level(result.get(NOTIFICATIONS_.LEVEL)),
                    result.get(NOTIFICATIONS_.MESSAGE),
                    result.get(NOTIFICATIONS_.CREATED_AT)
            );

    public void save(InputNotification inputNotification) {
        DSL.using(connection, SQLDialect.POSTGRES)
           .insertInto(NOTIFICATIONS_)
           .set(NotificationsMapper.INSTANCE.map(inputNotification))
           .execute();
    }

    /**
     * @param login of an account for which notifications is fetched
     */
    @SuppressWarnings("all")
    public List<Notification> findAllBy(String login) {
        var dsl = DSL.using(connection, SQLDialect.POSTGRES);
        var select = dsl.select()
                        .from(NOTIFICATIONS_)
                        .where(NOTIFICATIONS_.NOTIFIED_ACCOUNT.eq(login));
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

}
