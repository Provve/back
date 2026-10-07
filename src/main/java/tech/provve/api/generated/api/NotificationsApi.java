package tech.provve.api.generated.api;

import tech.provve.api.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.generated.dto.Error;
import tech.provve.api.generated.dto.Notifications;

import tech.provve.api.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface NotificationsApi {
    Future<ApiResponse<Void>> clearNotifications();

    Future<ApiResponse<Notifications>> listNotifications(CollectionAuthenticatedRequest collectionAuthenticatedRequest);
}
