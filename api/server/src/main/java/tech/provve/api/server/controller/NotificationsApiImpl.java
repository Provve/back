package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.NotificationsApi;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.List;

// Implement this class

public class NotificationsApiImpl implements NotificationsApi {

    public Future<ApiResponse<List<Notification>>> listNotifications(Pagination pagination) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> markNotificationsAsRead() {
        return Future.failedFuture(new HttpException(501));
    }

}
