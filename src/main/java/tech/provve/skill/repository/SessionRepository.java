package tech.provve.skill.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.jspecify.annotations.NullMarked;
import tech.provve.constants.Entity;

import java.util.Map;
import java.util.Optional;

@NullMarked
@Mapper
public interface SessionRepository {

    String SESSION = "session";

    @Insert("""
            INSERT INTO skill.session (owner, exam_name, created)
            VALUES (
                #{session.owner, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{session.examName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{session.started, typeHandler=org.apache.ibatis.type.InstantTypeHandler}
            )
            """)
    void save(@Param("session") Map<String, Object> session);

    @Select("""
            SELECT owner, exam_name, created
            FROM skill.session
            WHERE owner = #{owner, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = SESSION, value = {
            @Result(property = Entity.Session.OWNER, column = "owner", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Session.EXAM_NAME, column = "exam_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Session.STARTED, column = "created", typeHandler = org.apache.ibatis.type.InstantTypeHandler.class)
    })
    Optional<Map<String, Object>> find(@Param("owner") String owner);

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM skill.session
                WHERE owner = #{owner, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("owner") String owner);

}
