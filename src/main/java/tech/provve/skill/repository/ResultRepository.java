package tech.provve.skill.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.generated.dto.Filter;
import tech.provve.constants.Entity;

import java.util.List;
import java.util.Map;

@NullMarked
@Mapper
public interface ResultRepository {

    String RESULT = "result";

    @Insert("""
            INSERT INTO skill.result (exam_name, examinee, duration_minutes)
            VALUES (
                #{result.examName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{result.examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{result.durationMinutes, typeHandler=org.apache.ibatis.type.ObjectTypeHandler}::interval
            )
            """)
    void save(@Param("result") Map<String, Object> result);

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM skill.result
                WHERE examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("examinee") String examinee);

    @Select("""
            <script>
            SELECT exam_name, examinee, duration_minutes
            FROM skill.result
            <where>
                <if test="filter != null">
                    <foreach collection="filter.conditions" item="condition" separator="AND">
                        <choose>
                            <when test="condition.field == 'exam_name'">
                                exam_name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <otherwise>1 = 1</otherwise>
                        </choose>
                    </foreach>
                </if>
                <if test="previous != null and previous != ''">
                    AND exam_name &gt; #{previous, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                </if>
            </where>
            ORDER BY exam_name
            LIMIT #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </script>
            """)
    @Results(id = RESULT, value = {
            @Result(property = Entity.Result.EXAM_NAME, column = "exam_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Result.EXAMINEE, column = "examinee", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Result.DURATION_MINUTES, column = "duration_minutes", typeHandler = org.apache.ibatis.type.ObjectTypeHandler.class)
    })
    List<Map<String, Object>> getAll(@Param("filter") Filter filter,
                                     @Param("previous") String previous,
                                     @Param("pageSize") int pageSize);

    @Select("""
            <script>
            SELECT exam_name, examinee, duration_minutes
            FROM skill.result
            <where>
                <if test="filter != null">
                    <foreach collection="filter.conditions" item="condition" separator="AND">
                        <choose>
                            <when test="condition.field == 'exam_name'">
                                exam_name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <otherwise>1 = 1</otherwise>
                        </choose>
                    </foreach>
                </if>
                AND examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                <if test="previous != null and previous != ''">
                    AND exam_name &gt; #{previous, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                </if>
            </where>
            ORDER BY exam_name
            LIMIT #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </script>
            """)
    @ResultMap(RESULT)
    List<Map<String, Object>> getAllForExaminee(@Param("filter") Filter filter,
                                                @Param("examinee") String examinee,
                                                @Param("previous") String previous,
                                                @Param("pageSize") int pageSize);

}
