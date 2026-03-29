package tech.provve.skill.mapper.exam;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.skill.db.generated.tables.records.ExamRecord;
import tech.provve.skill.domain.entity.Exam;

@Mapper
public interface ExamJooqMapper {

    ExamJooqMapper INSTANCE = Mappers.getMapper(ExamJooqMapper.class);

    ExamRecord map(Exam from);

}
