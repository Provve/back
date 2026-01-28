package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationUpload;
import tech.provve.api.server.generated.dto.SessionCreatedNotification;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface SessionsApi {
    Future<ApiResponse<Notification>> createSession(CreateSessionRequest createSessionRequest);
    Future<ApiResponse<Integer>> getRandomValueForAntifraud();
    Future<ApiResponse<Void>> uploadObservation(ObservationUpload observationUpload);
}
