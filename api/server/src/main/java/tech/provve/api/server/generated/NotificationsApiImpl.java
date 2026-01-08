package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.List;

// Implement this class

public class NotificationsApiImpl implements NotificationsApi {

    public Future<ApiResponse<Void>> notificationsDelete() {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<Notification>>> notificationsGet(Pagination pagination) {
        return Future.failedFuture(new HttpException(501));
    }

}
