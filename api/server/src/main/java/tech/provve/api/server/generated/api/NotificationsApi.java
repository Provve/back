package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.List;

public interface NotificationsApi {
    Future<ApiResponse<List<Notification>>> listNotifications(Pagination pagination);
    Future<ApiResponse<Void>> markNotificationsAsRead();
}
