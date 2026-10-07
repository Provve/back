package tech.provve.api.generated.api;

import tech.provve.api.generated.dto.CreateSessionRequest;
import tech.provve.api.generated.dto.CreateSessionResponse;
import tech.provve.api.generated.dto.ObservationUploadRequest;
import tech.provve.api.generated.dto.ObservationUploadResponse;

import tech.provve.api.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface SessionsApi {
    Future<ApiResponse<CreateSessionResponse>> createSession(CreateSessionRequest createSessionRequest);

    Future<ApiResponse<ObservationUploadResponse>> uploadObservation(ObservationUploadRequest observationUploadRequest);
}
