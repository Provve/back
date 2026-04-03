package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.CreateSessionResponse;
import tech.provve.api.server.generated.dto.ObservationUploadRequest;
import tech.provve.api.server.generated.dto.ObservationUploadResponse;

public interface SessionsApi {
    Future<ApiResponse<CreateSessionResponse>> createSession(CreateSessionRequest createSessionRequest);
    Future<ApiResponse<ObservationUploadResponse>> uploadObservation(ObservationUploadRequest observationUploadRequest);
}
