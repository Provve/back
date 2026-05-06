package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.Error;
import tech.provve.api.server.generated.dto.Notifications;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface NotificationsApi {
    Future<ApiResponse<Void>> clearNotifications();
    Future<ApiResponse<Notifications>> listNotifications(CollectionAuthenticatedRequest collectionAuthenticatedRequest);
}
