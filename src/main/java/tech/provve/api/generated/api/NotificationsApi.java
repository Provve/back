package tech.provve.api.generated.api;

import io.vertx.core.Future;
import tech.provve.api.ApiResponse;
import tech.provve.api.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.generated.dto.Notifications;

public interface NotificationsApi {
    Future<ApiResponse<Void>> clearNotifications();
    Future<ApiResponse<Notifications>> listNotifications(CollectionAuthenticatedRequest collectionAuthenticatedRequest);
}
