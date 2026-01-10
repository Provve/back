package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface NotificationsApi {
    Future<ApiResponse<Void>> notificationsDelete();
    Future<ApiResponse<List<Notification>>> notificationsGet(Pagination pagination);
}
