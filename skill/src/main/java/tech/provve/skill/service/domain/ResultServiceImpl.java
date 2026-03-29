package tech.provve.skill.service.domain;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.ResultResponse;
import tech.provve.api.server.generated.dto.Results;
import tech.provve.skill.mapper.ResultResponseMapper;
import tech.provve.skill.repository.ResultRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final JwsParsingService jwsParsingService;

    @Override
    public Results list(CollectionAuthenticatedRequest request) {
        var login = jwsParsingService.parseAuth(request.getAuthToken(), JwsParsingService.JWT_SUBJECT);
        List<ResultResponse> all = resultRepository.getAll(request.getFilter(),
                                                           login, request.getPagination()
                                                                         .getPrevious(),
                                                           request.getPagination()
                                                                  .getSize())
                                                   .stream()
                                                   .map(ResultResponseMapper.INST::map)
                                                   .toList();
        var cursor = new Cursor(all.getLast()
                                   .getExamName());
        return new Results(all, cursor);
    }
}
