package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationPostRequest;
import tech.provve.api.server.generated.dto.SessionCreatedNotification;
import tech.provve.api.server.generated.dto.SessionStage2PostRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface SessionsApi {
    Future<ApiResponse<Void>> observationPost(ObservationPostRequest observationPostRequest);
    Future<ApiResponse<Integer>> sessionStage1Get();
    Future<ApiResponse<Notification>> sessionStage2Post(SessionStage2PostRequest sessionStage2PostRequest);
}
