package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationUpload;

public interface SessionsApi {
    Future<ApiResponse<Notification>> createSession(CreateSessionRequest createSessionRequest);
    Future<ApiResponse<Integer>> getRandomValueForAntifraud();
    Future<ApiResponse<Void>> uploadObservation(ObservationUpload observationUpload);
}
