package tech.provve.statemachine.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jspecify.annotations.NullMarked;
import tech.provve.constants.Entity;
import tech.provve.statemachine.domain.value.SaveExamState;

import java.util.List;
import java.util.Map;

@NullMarked
@Mapper
public interface SaveExamRepository {

    String SAVE_EXAM = "saveExam";

    @Insert("""
            INSERT INTO statemachine.save_exam (name, state, author, delayed_vote_json)
            VALUES (
                #{saveExam.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{saveExam.state, typeHandler=org.apache.ibatis.type.EnumTypeHandler}::statemachine.save_exam_state,
                #{saveExam.author, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{saveExam.delayedVoteJson, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("saveExam") Map<String, Object> saveExam);

    @Update("""
            UPDATE statemachine.save_exam
            SET state = #{state, typeHandler=org.apache.ibatis.type.EnumTypeHandler}::statemachine.save_exam_state
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateState(@Param("name") String name, @Param("state") SaveExamState state);

    /**
     * @return all of saved machines in non-final state
     */
    @Select("""
            SELECT name, state, author, delayed_vote_json
            FROM statemachine.save_exam
            """)
    @Results(id = SAVE_EXAM, value = {
            @Result(property = Entity.SaveExam.NAME, column = "name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.SaveExam.STATE, column = "state", typeHandler = org.apache.ibatis.type.EnumTypeHandler.class, javaType = SaveExamState.class),
            @Result(property = Entity.SaveExam.AUTHOR, column = "author", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.SaveExam.DELAYED_VOTE_JSON, column = "delayed_vote_json", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    List<Map<String, Object>> list();

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM statemachine.save_exam
                WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("name") String name);

    @Delete("""
            DELETE FROM statemachine.save_exam
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("name") String name);

}
