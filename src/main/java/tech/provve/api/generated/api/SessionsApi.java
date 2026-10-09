package tech.provve.api.generated.api;

import io.vertx.core.Future;
import tech.provve.api.ApiResponse;
import tech.provve.api.generated.dto.CreateSessionRequest;
import tech.provve.api.generated.dto.CreateSessionResponse;
import tech.provve.api.generated.dto.ObservationUploadRequest;
import tech.provve.api.generated.dto.ObservationUploadResponse;

public interface SessionsApi {
    Future<ApiResponse<CreateSessionResponse>> createSession(CreateSessionRequest createSessionRequest);
    Future<ApiResponse<ObservationUploadResponse>> uploadObservation(ObservationUploadRequest observationUploadRequest);
}
