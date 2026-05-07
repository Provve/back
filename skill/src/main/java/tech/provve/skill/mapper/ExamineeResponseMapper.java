package tech.provve.skill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.generated.dto.Examinee;
import tech.provve.api.server.generated.dto.ProfilePublicView;
import tech.provve.skill.domain.entity.Result;

import java.time.Duration;

@Mapper
public interface ExamineeResponseMapper {

    ExamineeResponseMapper INST = Mappers.getMapper(ExamineeResponseMapper.class);

    Examinee map(Result from, ProfilePublicView and);

    default Long map(Duration from) {
        return from.toMinutes();
    }

}
