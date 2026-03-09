package tech.provve.skill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.provve.skill.db.generated.tables.records.ExamAddVoteRecord;
import tech.provve.skill.db.generated.tables.records.VoteRecord;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Vote;

@Mapper
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    @Mapping(target = "type", expression = "java((short) from.getType().getCode())")
    VoteRecord map(Vote from);

    ExamAddVoteRecord map(Exam from, String voteName);

}
