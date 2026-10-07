package tech.provve.validation.repository;

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
public interface ObservationRepository {

    String OBSERVATION = "observation";

    @Insert("""
            INSERT INTO validation.observation (examinee, violations, cheated)
            VALUES (
                #{observation.examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{observation.violations, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{observation.cheated, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            )
            """)
    void save(@Param("observation") Map<String, Object> observation);

    @Select("""
            SELECT examinee, violations, cheated
            FROM validation.observation
            WHERE examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = OBSERVATION, value = {
            @Result(property = Entity.Observation.EXAMINEE, column = "examinee", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Observation.VIOLATIONS, column = "violations", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Observation.CHEATED, column = "cheated", typeHandler = org.apache.ibatis.type.BooleanTypeHandler.class)
    })
    Optional<Map<String, Object>> find(@Param("examinee") String examinee);

}
