package tech.provve.skill.repository;

import org.apache.ibatis.annotations.*;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.generated.dto.Filter;
import tech.provve.constants.Entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@NullMarked
@Mapper
public interface ResultRepository {

    String RESULT = "result";

    @Insert("""
            INSERT INTO skill.result (skill_name, examinee, duration_minutes, success)
            VALUES (
                #{result.skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{result.examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{result.durationMinutes, typeHandler=org.apache.ibatis.type.ObjectTypeHandler}::interval,
                #{result.success, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            )
            """)
    void save(@Param("result") Map<String, Object> result);

    @Select("""
            <script>
            SELECT skill_name, examinee, duration_minutes, success, created_at
            FROM skill.result
            <where>
                <if test="filter != null">
                    <foreach collection="filter.conditions" item="condition" separator="AND">
                        <choose>
                            <when test="condition.field == 'skill_name'">
                                skill_name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <otherwise>1 = 1</otherwise>
                        </choose>
                    </foreach>
                </if>
                <if test="previous != null and previous != ''">
                    AND skill_name &gt; #{previous, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                </if>
            </where>
            ORDER BY skill_name
            LIMIT #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </script>
            """)
    @Results(id = RESULT, value = {
            @Result(property = Entity.Result.SKILL_NAME, column = "skill_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Result.EXAMINEE, column = "examinee", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Result.DURATION_MINUTES, column = "duration_minutes", typeHandler = org.apache.ibatis.type.ObjectTypeHandler.class),
            @Result(property = Entity.Result.SUCCESS, column = "success", typeHandler = org.apache.ibatis.type.BooleanTypeHandler.class),
            @Result(property = Entity.Result.CREATED_AT, column = "created_at", typeHandler = org.apache.ibatis.type.OffsetDateTimeTypeHandler.class)
    })
    List<Map<String, Object>> getAll(@Param("filter") Filter filter,
                                     @Param("previous") String previous,
                                     @Param("pageSize") int pageSize);

    @Select("""
            <script>
            SELECT skill_name, examinee, duration_minutes, success, created_at
            FROM skill.result
            <where>
                <if test="filter != null">
                    <foreach collection="filter.conditions" item="condition" separator="AND">
                        <choose>
                            <when test="condition.field == 'skill_name'">
                                skill_name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <otherwise>1 = 1</otherwise>
                        </choose>
                    </foreach>
                </if>
                AND examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                <if test="previous != null and previous != ''">
                    AND skill_name &gt; #{previous, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                </if>
            </where>
            ORDER BY skill_name
            LIMIT #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </script>
            """)
    @ResultMap(RESULT)
    List<Map<String, Object>> getAllForExaminee(@Param("filter") Filter filter,
                                                @Param("examinee") String examinee,
                                                @Param("previous") String previous,
                                                @Param("pageSize") int pageSize);

    @Select("""
            SELECT skill_name, examinee, duration_minutes, success, created_at
            FROM skill.result
            WHERE skill_name = #{skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler}
              AND examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            ORDER BY created_at DESC
            LIMIT 1
            """)
    @ResultMap(RESULT)
    Optional<Map<String, Object>> find(@Param("skillName") String skillName,
                                       @Param("examinee") String examinee);

    /**
     * @return количество результатов (попыток) экзаменуемого по навыку
     */
    @Select("""
            SELECT COUNT(*)
            FROM skill.result
            WHERE skill_name = #{skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler}
              AND examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    int count(@Param("skillName") String skillName,
              @Param("examinee") String examinee);

}
