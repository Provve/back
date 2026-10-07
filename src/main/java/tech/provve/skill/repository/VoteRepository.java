package tech.provve.skill.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.generated.dto.Filter;
import tech.provve.constants.Entity;
import tech.provve.skill.domain.value.VoteType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@NullMarked
@Mapper
public interface VoteRepository {

    String VOTE = "vote";
    String VOTE_REACTIONS = "voteReactions";
    String VOTE_EXAM = "voteExam";

    default void save(Map<String, Object> vote) {
        VoteType voteType = VoteType.valueOf(vote.get(Entity.Vote.TYPE)
                                                 .toString());
        vote.put(Entity.Vote.TYPE, voteType);
        insertVote(vote);
        if (VoteType.ADD_EXAM.equals(voteType)) {
            insertExamAddVote(vote);
        }
    }

    @Insert("""
            INSERT INTO skill.vote (name, active, success, author, deadline, arguments, type, tags)
            VALUES (
                #{vote.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.active, typeHandler=org.apache.ibatis.type.BooleanTypeHandler},
                #{vote.success, typeHandler=org.apache.ibatis.type.BooleanTypeHandler},
                #{vote.author, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.deadline, typeHandler=org.apache.ibatis.type.LocalDateTimeTypeHandler},
                #{vote.arguments, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.type, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler, javaType=tech.provve.skill.domain.value.VoteType},
                #{vote.tags, typeHandler=org.apache.ibatis.type.ArrayTypeHandler}::text[]
            )
            """)
    void insertVote(@Param("vote") Map<String, Object> vote);

    @Insert("""
            INSERT INTO skill.exam_add_vote (vote_name, skill_name, description, private_archive_url, public_archive_url)
            VALUES (
                #{vote.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.exam.skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.exam.description, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.exam.privateArchiveUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{vote.exam.publicArchiveUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void insertExamAddVote(@Param("vote") Map<String, Object> vote);

    @Delete("""
            DELETE FROM skill.vote
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("name") String name);

    @Select("""
            SELECT name, active, success, author, deadline, arguments, type, tags
            FROM skill.vote
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = VOTE, value = {
            @Result(property = Entity.Vote.NAME, column = "name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Vote.ACTIVE, column = "active", typeHandler = org.apache.ibatis.type.BooleanTypeHandler.class),
            @Result(property = Entity.Vote.SUCCESS, column = "success", typeHandler = org.apache.ibatis.type.BooleanTypeHandler.class),
            @Result(property = Entity.Vote.AUTHOR, column = "author", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Vote.DEADLINE, column = "deadline", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = Entity.Vote.ARGUMENTS, column = "arguments", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Vote.TYPE, column = "type", typeHandler = org.apache.ibatis.type.EnumOrdinalTypeHandler.class, javaType = VoteType.class),
            @Result(property = Entity.Vote.TAGS, column = "tags", typeHandler = org.apache.ibatis.type.ArrayTypeHandler.class),
            @Result(property = Entity.Vote.REACTIONS, column = "name", javaType = Map.class,
                    one = @One(select = "selectReactionsTotal")),
            @Result(property = Entity.Vote.EXAM, column = "name", javaType = Map.class,
                    one = @One(select = "selectExamAddVote"))
    })
    Optional<Map<String, Object>> findByName(@Param("name") String name);

    @Select("""
            SELECT total_positive, total_negative
            FROM skill.get_reactions_total(#{name, typeHandler=org.apache.ibatis.type.StringTypeHandler})
            """)
    @Results(id = VOTE_REACTIONS, value = {
            @Result(property = Entity.VoteReactions.POSITIVE, column = "total_positive", typeHandler = org.apache.ibatis.type.IntegerTypeHandler.class),
            @Result(property = Entity.VoteReactions.NEGATIVE, column = "total_negative", typeHandler = org.apache.ibatis.type.IntegerTypeHandler.class)
    })
    Map<String, Object> selectReactionsTotal(@Param("name") String name);

    @Select("""
            SELECT skill_name, description, private_archive_url, public_archive_url
            FROM skill.exam_add_vote
            WHERE vote_name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = VOTE_EXAM, value = {
            @Result(property = Entity.Exam.SKILL_NAME, column = "skill_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.DESCRIPTION, column = "description", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.PRIVATE_ARCHIVE_URL, column = "private_archive_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Exam.PUBLIC_ARCHIVE_URL, column = "public_archive_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    Map<String, Object> selectExamAddVote(@Param("name") String name);

    /**
     * @return Существует ли активное голосование?
     */
    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM skill.vote
                WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                AND active = #{active, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            )
            """)
    boolean exists(@Param("name") String name, @Param("active") boolean active);

    @Select("""
            <script>
            SELECT name, active, success, author, deadline, arguments, type, tags
            FROM skill.vote
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
                            <when test="condition.field == 'active'">
                                active = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}::boolean
                            </when>
                            <when test="condition.field == 'author'">
                                author = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="condition.field == 'arguments' and condition.operator.toString() == 'EQ'">
                                arguments = #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                            </when>
                            <when test="condition.field == 'arguments' and condition.operator.toString() == 'LIKE'">
                                to_tsvector('russian', arguments) @@ plainto_tsquery('russian', #{condition.value, typeHandler=org.apache.ibatis.type.StringTypeHandler})
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
    @ResultMap(VOTE)
    List<Map<String, Object>> getAll(@Param("filter") Filter filter,
                                     @Param("previous") String previous,
                                     @Param("pageSize") int pageSize);

    /**
     * @param voter    who is voting
     * @param reaction +/-
     */
    @Insert("""
            INSERT INTO skill.reactions (vote_name, voter, reaction)
            VALUES (
                #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{voter, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                CASE WHEN #{reaction, typeHandler=org.apache.ibatis.type.BooleanTypeHandler} THEN B'1' ELSE B'0' END
            )
            """)
    void setReaction(@Param("name") String name, @Param("voter") String voter, @Param("reaction") boolean reaction);

    /**
     * @return Существует ли реакция данного участника на голосование?
     */
    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM skill.reactions
                WHERE vote_name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
                AND voter = #{voter, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean reactionExists(@Param("name") String name, @Param("voter") String voter);

    /**
     * Automatically sets <code>active = false</code> by business rule
     */
    @Update("""
            UPDATE skill.vote
            SET active = FALSE,
                success = #{success, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateSuccess(@Param("name") String name, @Param("success") boolean success);

}
