package tech.provve.validation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.validation.domain.entity.Observation;

@Mapper
public interface ObservationMapper {

    ObservationMapper INST = Mappers.getMapper(ObservationMapper.class);

    Observation map(tech.provve.api.server.generated.dto.Observation from);

}
