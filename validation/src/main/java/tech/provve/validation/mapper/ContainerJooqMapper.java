package tech.provve.validation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.validation.db.generated.tables.records.ContainerRecord;
import tech.provve.validation.domain.value.ContainerView;

@Mapper
public interface ContainerJooqMapper {

    ContainerJooqMapper INST = Mappers.getMapper(ContainerJooqMapper.class);

    ContainerRecord map(ContainerView from);

}
