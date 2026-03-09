package tech.provve.skill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.skill.db.generated.tables.records.ExamRecord;
import tech.provve.skill.domain.entity.Exam;

@Mapper
public interface ExamMapper {

    ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

    ExamRecord map(Exam from);

}
