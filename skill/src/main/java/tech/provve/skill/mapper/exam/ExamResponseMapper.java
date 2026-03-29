package tech.provve.skill.mapper.exam;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.generated.dto.ExamResponse;
import tech.provve.skill.domain.entity.Exam;

@Mapper
public interface ExamResponseMapper {

    ExamResponseMapper INST = Mappers.getMapper(ExamResponseMapper.class);

    ExamResponse map(Exam from);

}
