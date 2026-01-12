package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.HttpException;

import java.util.List;
import java.util.Map;

// Implement this class

public class NotificationsApiImpl implements NotificationsApi {

    public Future<ApiResponse<List<Notification>>> listNotifications(Pagination pagination) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> markNotificationsAsRead() {
        return Future.failedFuture(new HttpException(501));
    }

}
