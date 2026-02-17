package tech.provve.notification.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.notification.domain.entity.InputNotification;
import tech.provve.notification.mapper.NotificationMapper;

import java.util.List;

import static tech.provve.notification.db.generated.tables.Notification.NOTIFICATION_;

@Singleton
@RequiredArgsConstructor
public class NotificationRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Notification> outputMapper = result ->
            new Notification(
                    result.get(NOTIFICATION_.ID),
                    NotificationMapper.INSTANCE.level(result.get(NOTIFICATION_.LEVEL)),
                    result.get(NOTIFICATION_.MESSAGE),
                    result.get(NOTIFICATION_.CREATED_AT)
            );

    public void save(InputNotification inputNotification) {
        dsl.insertInto(NOTIFICATION_)
           .set(NotificationMapper.INSTANCE.map(inputNotification))
           .execute();
    }

    /**
     * @param login of an account for which NOTIFICATION is fetched
     */
    @SuppressWarnings("all")
    public List<Notification> findAllBy(String login) {
        var select = dsl.select()
                        .from(NOTIFICATION_)
                        .where(NOTIFICATION_.NOTIFIED_ACCOUNT.eq(login));
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }

}
