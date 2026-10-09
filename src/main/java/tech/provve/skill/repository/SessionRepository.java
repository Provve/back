package tech.provve.skill.repository;

import org.apache.ibatis.annotations.*;
import org.jspecify.annotations.NullMarked;
import tech.provve.constants.Entity;

import java.util.Map;
import java.util.Optional;

@NullMarked
@Mapper
public interface SessionRepository {

    String SESSION = "session";

    @Insert("""
            INSERT INTO skill.session (owner, skill_name, created)
            VALUES (
                #{session.owner, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{session.skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{session.started, typeHandler=org.apache.ibatis.type.InstantTypeHandler}
            )
            """)
    void save(@Param("session") Map<String, Object> session);

    @Select("""
            SELECT owner, skill_name, created
            FROM skill.session
            WHERE owner = #{owner, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = SESSION, value = {
            @Result(property = Entity.Session.OWNER, column = "owner", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Session.SKILL_NAME, column = "skill_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Session.STARTED, column = "created", typeHandler = org.apache.ibatis.type.InstantTypeHandler.class)
    })
    Optional<Map<String, Object>> find(@Param("owner") String owner);

    @Delete("""
            DELETE FROM skill.session
            WHERE owner = #{owner, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("owner") String owner);

}
