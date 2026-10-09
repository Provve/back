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
public interface SkillRepository {

    String SKILL = "skill";

    @Insert("""
            INSERT INTO skill.skill (name, description, private_archive_url, public_archive_url, tags)
            VALUES (
                #{skill.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{skill.description, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{skill.privateArchiveUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{skill.publicArchiveUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{skill.tags, typeHandler=org.apache.ibatis.type.ArrayTypeHandler}::text[]
            )
            """)
    void save(@Param("skill") Map<String, Object> skill);

    @Select("""
            SELECT name, description, private_archive_url, public_archive_url, tags
            FROM skill.skill
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = SKILL, value = {
            @Result(property = Entity.Skill.NAME, column = "name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Skill.DESCRIPTION, column = "description", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Skill.PRIVATE_ARCHIVE_URL, column = "private_archive_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Skill.PUBLIC_ARCHIVE_URL, column = "public_archive_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Skill.TAGS, column = "tags", typeHandler = org.apache.ibatis.type.ArrayTypeHandler.class)
    })
    Optional<Map<String, Object>> find(@Param("name") String name);

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM skill.skill
                WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("name") String name);

    @Select("""
            <script>
            SELECT name, description, private_archive_url, public_archive_url, tags
            FROM skill.skill
            <where>
                <if test="filter != null">
                    <foreach collection="filter.conditions" item="condition" separator="AND">
                        <choose>
                            <when test="(condition.field == 'name' or condition.field == 'examName') and condition.operator.toString() == 'EQ'">
                                name = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="(condition.field == 'name' or condition.field == 'examName') and condition.operator.toString() == 'LIKE'">
                                to_tsvector('russian', name) @@ plainto_tsquery('russian', #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler})
                            </when>
                            <when test="condition.field == 'description' and condition.operator.toString() == 'EQ'">
                                description = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="condition.field == 'description' and condition.operator.toString() == 'LIKE'">
                                to_tsvector('russian', description) @@ plainto_tsquery('russian', #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler})
                            </when>
                            <when test="condition.field == 'tags'">
                                tags &amp;&amp; string_to_array(#{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}, ',')::text[]
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
    @ResultMap(SKILL)
    List<Map<String, Object>> getAll(@Param("filter") Filter filter,
                                     @Param("previous") String previous,
                                     @Param("pageSize") int pageSize);

    @Delete("""
            DELETE FROM skill.skill
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("name") String name);

}
