package tech.provve.notification.repository;

import org.apache.ibatis.annotations.*;
import tech.provve.constants.Entity;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotificationRepository {

    String NOTIFICATION = "notification";

    @Insert("""
            INSERT INTO notification.notification (notified_account, level, message)
            VALUES (
                #{inputNotification.receiver, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{inputNotification.level, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler, javaType=tech.provve.notification.domain.value.NotificationLevel},
                #{inputNotification.message, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("inputNotification") Map<String, Object> inputNotification);

    /**
     * @param login of an account for which NOTIFICATION is fetched
     */
    @Select("""
            <script>
            SELECT id, level, message, created_at
            FROM notification.notification
            WHERE notified_account = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            <if test="previous != null and previous != ''">
                AND id &gt; #{previous, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </if>
            ORDER BY id
            LIMIT #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </script>
            """)
    @Results(id = NOTIFICATION, value = {
            @Result(property = Entity.Notification.ID, column = "id", typeHandler = org.apache.ibatis.type.IntegerTypeHandler.class),
            @Result(property = Entity.Notification.LEVEL, column = "level", typeHandler = org.apache.ibatis.type.ShortTypeHandler.class),
            @Result(property = Entity.Notification.MESSAGE, column = "message", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Notification.CREATED_AT, column = "created_at", typeHandler = org.apache.ibatis.type.OffsetDateTimeTypeHandler.class)
    })
    List<Map<String, Object>> findAllBy(@Param("login") String login,
                                        @Param("previous") String previous,
                                        @Param("pageSize") int pageSize);

}
