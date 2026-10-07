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
import tech.provve.statemachine.domain.value.CheckSolutionState;

import java.util.List;
import java.util.Map;

@NullMarked
@Mapper
public interface CheckSolutionRepository {

    String CHECK_SOLUTION = "checkSolution";

    @Insert("""
            INSERT INTO statemachine.check_solution (name, state, examinee)
            VALUES (
                #{checkSolution.name, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{checkSolution.state, typeHandler=org.apache.ibatis.type.EnumTypeHandler}::statemachine.check_solution_state,
                #{checkSolution.examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("checkSolution") Map<String, Object> checkSolution);

    @Update("""
            UPDATE statemachine.check_solution
            SET state = #{state, typeHandler=org.apache.ibatis.type.EnumTypeHandler}::statemachine.check_solution_state
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateState(@Param("name") String name, @Param("state") CheckSolutionState state);

    /**
     * @return all of saved machines in non-final state
     */
    @Select("""
            SELECT name, state, examinee
            FROM statemachine.check_solution
            """)
    @Results(id = CHECK_SOLUTION, value = {
            @Result(property = Entity.CheckSolution.NAME, column = "name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.CheckSolution.STATE, column = "state", typeHandler = org.apache.ibatis.type.EnumTypeHandler.class, javaType = CheckSolutionState.class),
            @Result(property = Entity.CheckSolution.EXAMINEE, column = "examinee", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    List<Map<String, Object>> list();

    @Select("""
            SELECT EXISTS(
                SELECT 1 FROM statemachine.check_solution
                WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    boolean exists(@Param("name") String name);

    @Delete("""
            DELETE FROM statemachine.check_solution
            WHERE name = #{name, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("name") String name);

}
