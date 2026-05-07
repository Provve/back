package tech.provve.skill.service.domain;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.exception.AccessDenied;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.api.server.generated.dto.*;
import tech.provve.skill.domain.entity.Result;
import tech.provve.skill.mapper.ExamineeResponseMapper;
import tech.provve.skill.mapper.ResultResponseMapper;
import tech.provve.skill.repository.ResultRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.provve.accounts.service.JwsParsingService.PREMIUM;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final JwsParsingService jwsParsingService;
    private final AccountService accountService;

    @Override
    public Results list(CollectionAuthenticatedRequest request) {
        var login = jwsParsingService.parseAuth(request.getAuthToken(), JwsParsingService.JWT_SUBJECT);
        var pagination = request.getPagination();
        List<ResultResponse> all = resultRepository.getAllForExaminee(request.getFilter(), login, pagination.getPrevious(), pagination.getSize())
                                                   .stream()
                                                   .map(ResultResponseMapper.INST::map)
                                                   .toList();
        if (all.isEmpty()) {
            return new Results(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getExamName());
        return new Results(all, cursor);
    }

    @Override
    public Examinees listExaminees(CollectionAuthenticatedRequest request) throws AccessDenied {
        boolean premium = Boolean.parseBoolean(jwsParsingService.parseAuth(request.getAuthToken(), PREMIUM));
        if (!premium) {
            throw new AccessDenied("You have to buy premium access first");
        }

        var pagination = request.getPagination();
        List<Result> results = resultRepository.getAll(request.getFilter(), pagination.getPrevious(), pagination.getSize())
                                               .stream()
                                               .toList();

        Map<Result, ProfilePublicView> profiles = HashMap.newHashMap(results.size());
        results.stream()
               .map(result -> Map.of(
                       result,
                       accountService.viewPublicProfile(result.examinee())
               ))
               .forEach(profiles::putAll);

        List<Examinee> examinees = profiles.entrySet()
                                           .stream()
                                           .map(entry -> ExamineeResponseMapper.INST.map(entry.getKey(), entry.getValue()))
                                           .toList();
        if (results.isEmpty()) {
            return new Examinees(examinees, new Cursor(""));
        }
        var cursor = new Cursor(results.getLast()
                                       .examinee());
        return new Examinees(examinees, cursor);
    }
}
