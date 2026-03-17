package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.SessionsApi;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.CreateSessionResponse;
import tech.provve.api.server.generated.dto.ObservationUpload;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.skill.exception.ExamNotFound;
import tech.provve.skill.exception.ExamPassTwice;
import tech.provve.skill.service.application.SessionService;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SessionsController implements SessionsApi {

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

    public Future<ApiResponse<Void>> uploadObservation(ObservationUpload observationUpload) {
        return Future.failedFuture(new HttpException(501));
    }

}
