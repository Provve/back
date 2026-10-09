package tech.provve.api.controller;

import io.vertx.core.Future;
import tech.provve.accounts.JwsParsing;
import tech.provve.api.Antifraud;
import tech.provve.api.ApiResponse;
import tech.provve.api.exception.HttpException;
import tech.provve.api.exception.ValidationError;
import tech.provve.api.generated.api.SessionsApi;
import tech.provve.api.generated.dto.CreateSessionRequest;
import tech.provve.api.generated.dto.CreateSessionResponse;
import tech.provve.api.generated.dto.ObservationUploadRequest;
import tech.provve.api.generated.dto.ObservationUploadResponse;
import tech.provve.skill.Session;
import tech.provve.skill.exception.SkillNotFound;
import tech.provve.util.Jackson;
import tech.provve.util.Validation;

import java.util.Map;

import static tech.provve.accounts.JwsParsing.JWT_SUBJECT;

public class SessionsController implements SessionsApi {

    @Override
    public Future<ApiResponse<CreateSessionResponse>> createSession(CreateSessionRequest createSessionRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(createSessionRequest);

            String failureMessage = Validation.validateCreateSessionRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);
            var response = Session.create(createSessionRequest);
            return Future.succeededFuture(new ApiResponse<>(200, response));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (SkillNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

    @Override
    public Future<ApiResponse<ObservationUploadResponse>> uploadObservation(ObservationUploadRequest observationUploadRequest) {
        boolean legit = Antifraud.check(observationUploadRequest.getSig(),
                                        observationUploadRequest.getNonce(),
                                        observationUploadRequest.getObservation());
        if (observationUploadRequest.getObservation()
                                    .getCheated()) {
            return Future.failedFuture(new HttpException(400));
        }

        if (!legit) {
            return Future.failedFuture(new HttpException(403));
        }

        tech.provve.validation.Validation.observed(observationUploadRequest.getObservation());
        var examinee = JwsParsing.parseTrust(observationUploadRequest.getAuthToken(), JWT_SUBJECT);
        var trustToken = Antifraud.trust(examinee);
        var response = new ObservationUploadResponse(trustToken);
        return Future.succeededFuture(new ApiResponse<>(200, response));
    }

}
