package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.SkillsApi;
import tech.provve.api.server.generated.dto.*;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.skill.mapper.exam.ExamResponseMapper;
import tech.provve.skill.repository.ExamRepository;
import tech.provve.skill.service.domain.ExamService;
import tech.provve.skill.service.domain.ResultService;
import tech.provve.skill.service.domain.SkillService;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkillsController implements SkillsApi {

    private final InputValidator inputValidator;
    private final SkillService skillService;
    private final ResultService resultService;
    private final ExamService examService;


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
    public Future<ApiResponse<Void>> submitExamSolution(String name, FileUpload solution) {
        // использовать tech.provve.api.server.factory.S3Factory.s3AsyncClient
        return null;
    }

    @Override
    public Future<ApiResponse<ResultResponse>> viewExamResult(String examName) {
        return null;
    }

}
