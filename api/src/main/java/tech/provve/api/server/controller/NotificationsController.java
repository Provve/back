package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import jakarta.inject.Singleton;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.NotificationsApi;
import tech.provve.api.server.generated.dto.ListNotifications;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.List;

@Singleton
public class NotificationsController implements NotificationsApi {

    @Override
    public Future<ApiResponse<List<Notification>>> listNotifications(ListNotifications listNotifications) {
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> markNotificationsAsRead() {
        return null;
    }
}
