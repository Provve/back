package tech.provve.api.server.controller;

import io.vertx.core.Future;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.SessionsApi;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.CreateSessionResponse;
import tech.provve.api.server.generated.dto.ObservationUploadRequest;
import tech.provve.api.server.generated.dto.ObservationUploadResponse;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.AntifraudLegitimacyChecker;
import tech.provve.api.server.service.InputValidator;
import tech.provve.skill.exception.ExamNotFound;
import tech.provve.skill.exception.ExamPassTwice;
import tech.provve.skill.service.application.SessionService;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SessionsController implements SessionsApi {

    private final AntifraudLegitimacyChecker antifraudLegitimacyChecker;
    private final SessionService sessionService;
    private final InputValidator validator;

    @Override
    public Future<ApiResponse<CreateSessionResponse>> createSession(CreateSessionRequest createSessionRequest) {
        try {
            validator.validate(InputValidatorMapper.INSTANCE.map(createSessionRequest));
            var response = sessionService.create(createSessionRequest);
            return Future.succeededFuture(new ApiResponse<>(200, response));
        } catch (ValidationError e) {
            return Future.failedFuture(new tech.provve.api.server.exception.HttpException(e, 400));
        } catch (ExamNotFound e) {
            return Future.failedFuture(new tech.provve.api.server.exception.HttpException(e, 404));
        } catch (ExamPassTwice e) {
            return Future.failedFuture(new tech.provve.api.server.exception.HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<ObservationUploadResponse>> uploadObservation(ObservationUploadRequest observationUploadRequest) {
        boolean legit = antifraudLegitimacyChecker.check(observationUploadRequest.getSig(),
                                                         observationUploadRequest.getNonce(),
                                                         observationUploadRequest.getObservation());
        if (!legit) {
            return Future.failedFuture(new HttpException(403));
        }
        return Future.succeededFuture(new ApiResponse<>(200));
    }

}
