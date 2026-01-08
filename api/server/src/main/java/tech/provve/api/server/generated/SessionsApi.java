package tech.provve.api.server.generated;

import io.vertx.core.Future;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationPostRequest;
import tech.provve.api.server.generated.dto.SessionStage2PostRequest;

public interface SessionsApi {

    Future<ApiResponse<Void>> observationPost(ObservationPostRequest observationPostRequest);

    Future<ApiResponse<Integer>> sessionStage1Get();

    Future<ApiResponse<Notification>> sessionStage2Post(SessionStage2PostRequest sessionStage2PostRequest);
}
