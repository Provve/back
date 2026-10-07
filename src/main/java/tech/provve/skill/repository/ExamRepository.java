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
import java.util.Optional;

@NullMarked
@Mapper
public interface ExamRepository {

    String EXAM = "exam";

    @Insert("""
            INSERT INTO skill.exam (name, skill_name, description, private_archive_url, public_archive_url)
            VALUES (
                #{exam.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{exam.skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{exam.description, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{exam.privateArchiveUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{exam.publicArchiveUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("exam") Map<String, Object> exam);

    @Select("""
            SELECT name, skill_name, description, private_archive_url, public_archive_url
            FROM skill.exam
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = EXAM, value = {
            @Result(property = Entity.Exam.NAME, column = "name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.SKILL_NAME, column = "skill_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.DESCRIPTION, column = "description", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.PRIVATE_ARCHIVE_URL, column = "private_archive_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.PUBLIC_ARCHIVE_URL, column = "public_archive_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    Optional<Map<String, Object>> find(@Param("name") String name);

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM skill.exam
                WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("name") String name);

    @Select("""
            <script>
            SELECT name, skill_name, description, private_archive_url, public_archive_url
            FROM skill.exam
            <where>
                <if test="filter != null">
                    <foreach collection="filter.conditions" item="condition" separator="AND">
                        <choose>
                            <when test="condition.field == 'examName' and condition.operator.toString() == 'EQ'">
                                name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="condition.field == 'examName' and condition.operator.toString() == 'LIKE'">
                                to_tsvector('russian', name) @@ plainto_tsquery('russian', #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler})
                            </when>
                            <when test="condition.field == 'skill_name'">
                                skill_name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="condition.field == 'description' and condition.operator.toString() == 'EQ'">
                                description = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="condition.field == 'description' and condition.operator.toString() == 'LIKE'">
                                to_tsvector('russian', description) @@ plainto_tsquery('russian', #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler})
                            </when>
                            <otherwise>1 = 1</otherwise>
                        </choose>
                    </foreach>
                </if>
                <if test="previous != null and previous != ''">
                    AND name &gt; #{previous, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                </if>
            </where>
            ORDER BY name
            LIMIT #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            </script>
            """)
    @ResultMap(EXAM)
    List<Map<String, Object>> getAll(@Param("filter") Filter filter,
                                     @Param("previous") String previous,
                                     @Param("pageSize") int pageSize);

}
