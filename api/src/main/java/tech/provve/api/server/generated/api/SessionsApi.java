package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.ObservationUpload;

public interface SessionsApi {
    Future<ApiResponse<Void>> createSession(CreateSessionRequest createSessionRequest);
    Future<ApiResponse<Void>> uploadObservation(ObservationUpload observationUpload);
}
