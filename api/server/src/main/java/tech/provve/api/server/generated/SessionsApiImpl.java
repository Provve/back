package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationPostRequest;
import tech.provve.api.server.generated.dto.SessionStage2PostRequest;

// Implement this class

public class SessionsApiImpl implements SessionsApi {

    public Future<ApiResponse<Void>> observationPost(ObservationPostRequest observationPostRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Integer>> sessionStage1Get() {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Notification>> sessionStage2Post(SessionStage2PostRequest sessionStage2PostRequest) {
        return Future.failedFuture(new HttpException(501));
    }

}
