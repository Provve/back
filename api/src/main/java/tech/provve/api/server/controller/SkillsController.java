package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.SkillsApi;
import tech.provve.api.server.generated.dto.*;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.api.server.validation.dto.CollectionRequestAuthenticated;
import tech.provve.skill.mapper.ResultResponseMapper;
import tech.provve.skill.mapper.exam.ExamResponseMapper;
import tech.provve.skill.repository.ExamRepository;
import tech.provve.skill.repository.ResultRepository;
import tech.provve.skill.repository.SkillRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkillsController implements SkillsApi {

    private final SkillRepository skillRepository;
    private final ExamRepository examRepository;
    private final ResultRepository resultRepository;
    private final InputValidator inputValidator;
    private final JwsParsingService jwsParsingService;

    @Override
    public Future<ApiResponse<Exams>> listExams(String skillName, CollectionRequest collectionRequest) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(collectionRequest));

            List<ExamResponse> all = examRepository.getAll(collectionRequest.getFilter(),
                                                           collectionRequest.getPagination()
                                                                            .getPrevious(),
                                                           collectionRequest.getPagination()
                                                                            .getSize())
                                                   .stream()
                                                   .map(ExamResponseMapper.INST::map)
                                                   .toList();
            var cursor = new Cursor(all.getLast()
                                       .getName());
            return Future.succeededFuture(new ApiResponse<>(200, new Exams(all, cursor)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Results>> listResults(String skillName, CollectionRequest collectionRequest) {
        try {
            inputValidator.validate(new CollectionRequestAuthenticated(InputValidatorMapper.INSTANCE.map(collectionRequest),
                                                                       collectionRequest.getAuthToken()));
            var login = jwsParsingService.parseAuth(collectionRequest.getAuthToken(), JwsParsingService.JWT_SUBJECT);
            List<ResultResponse> all = resultRepository.findAll(collectionRequest.getFilter(),
                                                                login, collectionRequest.getPagination()
                                                                                        .getPrevious(),
                                                                collectionRequest.getPagination()
                                                                                 .getSize())
                                                       .stream()
                                                       .map(ResultResponseMapper.INST::map)
                                                       .toList();
            var cursor = new Cursor(all.getLast()
                                       .getExamName());
            return Future.succeededFuture(new ApiResponse<>(200, new Results(all, cursor)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(collectionRequest));
            List<SkillResponse> all = skillRepository.getAll(collectionRequest.getFilter(),
                                                             collectionRequest.getPagination()
                                                                              .getPrevious(),
                                                             collectionRequest.getPagination()
                                                                              .getSize())
                                                     .stream()
                                                     .map(skill -> new SkillResponse(skill.name(), skill.tags()))
                                                     .toList();
            var cursor = new Cursor(all.getLast()
                                       .getName());
            return Future.succeededFuture(new ApiResponse<>(200, new Skills(all, cursor)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> submitExamSolution(String name, FileUpload solution) {
        // использовать метод multipart для загрузки архива в s3 и клиент AWS CRT-based
        return null;
    }

    @Override
    public Future<ApiResponse<ResultResponse>> viewExamResult(String examName) {
        return null;
    }

}
