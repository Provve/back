package tech.provve.skill.mapper.vote;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.generated.dto.VoteResponse;
import tech.provve.skill.domain.entity.Vote;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper
public interface VoteResponseMapper {

    VoteResponseMapper INST = Mappers.getMapper(VoteResponseMapper.class);

    @Mapping(target = "examAdd", source = "exam")
    VoteResponse map(Vote from);

    default OffsetDateTime map(LocalDateTime from) {
        return from.atOffset(ZoneOffset.UTC);
    }

}
