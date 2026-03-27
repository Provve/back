package tech.provve.skill.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.generated.dto.ResultResponse;
import tech.provve.skill.domain.entity.Result;

@Mapper
public interface ResultResponseMapper {

    ResultResponseMapper INST = Mappers.getMapper(ResultResponseMapper.class);

    default ResultResponse map(Result from) {
        return new ResultResponse(from.examName(),
                                  from.duration()
                                      .toMinutes());
    }

}
