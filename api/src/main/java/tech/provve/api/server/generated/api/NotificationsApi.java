package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.ListNotifications;
import tech.provve.api.server.generated.dto.Notification;

import java.util.List;

public interface NotificationsApi {
    Future<ApiResponse<List<Notification>>> listNotifications(ListNotifications listNotifications);
    Future<ApiResponse<Void>> markNotificationsAsRead();
}
