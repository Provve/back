package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import jakarta.inject.Singleton;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.SessionsApi;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationUpload;

@Singleton
public class SessionsController implements SessionsApi {

    public Future<ApiResponse<Notification>> createSession(CreateSessionRequest createSessionRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Integer>> getRandomValueForAntifraud() {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> uploadObservation(ObservationUpload observationUpload) {
        return Future.failedFuture(new HttpException(501));
    }

}
