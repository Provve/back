package tech.provve.validation.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tech.provve.constants.Entity;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface ContainerRepository {

    String CONTAINER = "container";

    @Insert("""
            INSERT INTO validation.container (examinee, exam_name, container_id)
            VALUES (
                #{container.examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{container.examName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{container.containerId, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("container") Map<String, Object> containerView);

    @Delete("""
            DELETE FROM validation.container
            WHERE examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("examinee") String examinee);

    @Select("""
            SELECT examinee, exam_name, container_id
            FROM validation.container
            WHERE examinee = #{examinee, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = CONTAINER, value = {
            @Result(property = Entity.Container.EXAMINEE, column = "examinee", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Container.EXAM_NAME, column = "exam_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Container.CONTAINER_ID, column = "container_id", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    Optional<Map<String, Object>> find(@Param("examinee") String examinee);

}
