package tech.provve.skill.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.provve.skill.db.generated.tables.records.CommentRecord;
import tech.provve.skill.domain.entity.Comment;

import java.time.ZoneOffset;

@Mapper(imports = ZoneOffset.class)
public interface CommentJooqMapper {

    CommentJooqMapper INSTANCE = Mappers.getMapper(CommentJooqMapper.class);

    @Mapping(target = "created", expression = "java(from.created().atOffset(ZoneOffset.UTC))")
    CommentRecord map(Comment from);
}
