package tech.provve.statemachine.repository;

import org.apache.ibatis.annotations.*;
import org.jspecify.annotations.NullMarked;
import tech.provve.constants.Entity;
import tech.provve.statemachine.domain.value.SaveSkillState;

import java.util.List;
import java.util.Map;

@NullMarked
@Mapper
public interface SaveSkillRepository {

    String SAVE_SKILL = "saveSkill";

    @Insert("""
            INSERT INTO statemachine.save_skill (name, state, author, delayed_vote_json)
            VALUES (
                #{saveSkill.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{saveSkill.state, typeHandler=org.apache.ibatis.type.EnumTypeHandler}::statemachine.save_skill_state,
                #{saveSkill.author, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{saveSkill.delayedVoteJson, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("saveSkill") Map<String, Object> saveSkill);

    @Update("""
            UPDATE statemachine.save_skill
            SET state = #{state, typeHandler=org.apache.ibatis.type.EnumTypeHandler}::statemachine.save_skill_state
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateState(@Param("name") String name, @Param("state") SaveSkillState state);

    /**
     * @return all of saved machines in non-final state
     */
    @Select("""
            SELECT name, state, author, delayed_vote_json
            FROM statemachine.save_skill
            """)
    @Results(id = SAVE_SKILL, value = {
            @Result(property = Entity.SaveSkill.NAME, column = "name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.SaveSkill.STATE, column = "state", typeHandler = org.apache.ibatis.type.EnumTypeHandler.class, javaType = SaveSkillState.class),
            @Result(property = Entity.SaveSkill.AUTHOR, column = "author", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.SaveSkill.DELAYED_VOTE_JSON, column = "delayed_vote_json", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    List<Map<String, Object>> list();

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM statemachine.save_skill
                WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("name") String name);

    @Delete("""
            DELETE FROM statemachine.save_skill
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("name") String name);

}
