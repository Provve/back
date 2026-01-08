package tech.provve.api.server.generated;

import io.vertx.core.Future;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.List;

public interface NotificationsApi {

    Future<ApiResponse<Void>> notificationsDelete();

    Future<ApiResponse<List<Notification>>> notificationsGet(Pagination pagination);
}
