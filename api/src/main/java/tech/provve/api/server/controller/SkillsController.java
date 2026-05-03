package tech.provve.api.server.controller;

import io.vertx.core.Future;
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
import tech.provve.skill.service.domain.ExamService;
import tech.provve.skill.service.domain.ResultService;
import tech.provve.skill.service.domain.SkillService;
import tech.provve.validation.service.ValidationService;

import java.nio.file.Path;

import static tech.provve.accounts.service.JwsParsingService.JWT_SUBJECT;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkillsController implements SkillsApi {

    private final InputValidator inputValidator;
    private final SkillService skillService;
    private final ResultService resultService;
    private final ExamService examService;
    private final ValidationService validationService;
    private final JwsParsingService jwsParsingService;

    @Override
    public Future<ApiResponse<ResultResponse>> getExamResult(String examName) {
        return null;
    }

    @Override
    public Future<ApiResponse<Exams>> listExams(String skillName, CollectionRequest collectionRequest) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(collectionRequest));
            return Future.succeededFuture(new ApiResponse<>(200, examService.list(collectionRequest)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest request) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(request));
            return Future.succeededFuture(new ApiResponse<>(200, resultService.list(request)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(collectionRequest));
            return Future.succeededFuture(new ApiResponse<>(200, skillService.list(collectionRequest)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> submitExamSolution(String examName, SubmitExamSolutionRequest submitExamSolutionRequest) {
        var examinee = jwsParsingService.parseTrust(submitExamSolutionRequest.getTrustToken(), JWT_SUBJECT);
        var accepted = validationService.validate(examinee,
                                                  examName,
                                                  Path.of(submitExamSolutionRequest.getSolution()
                                                                                   .uploadedFileName()));
        if (accepted) {
            return Future.succeededFuture(new ApiResponse<>(200));
        }

        return Future.succeededFuture(new ApiResponse<>(202));
    }

}
