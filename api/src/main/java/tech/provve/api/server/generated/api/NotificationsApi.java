package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Notifications;

public interface NotificationsApi {

    Future<ApiResponse<Void>> clearNotifications();

    Future<ApiResponse<Notifications>> listNotifications(CollectionRequest collectionRequest);
}
