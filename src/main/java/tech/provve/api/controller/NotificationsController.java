package tech.provve.api.controller;

import io.vertx.core.Future;
import tech.provve.accounts.JwsParsing;
import tech.provve.api.ApiResponse;
import tech.provve.api.exception.HttpException;
import tech.provve.api.exception.ValidationError;
import tech.provve.api.generated.api.NotificationsApi;
import tech.provve.api.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.generated.dto.Notifications;
import tech.provve.notification.NotificationSending;
import tech.provve.util.Jackson;
import tech.provve.util.Validation;

import java.util.Map;

public class NotificationsController implements NotificationsApi {

    @Override
    public Future<ApiResponse<Void>> clearNotifications() {
        return null;
    }

    @Override
    public Future<ApiResponse<Notifications>> listNotifications(CollectionAuthenticatedRequest request) {
        try {
            Map<String, Object> params = Jackson.convertToMap(request);

            String failureMessage = Validation.validateCollectionAuthenticatedRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) {
                throw new ValidationError(failureMessage);
            }

            var login = JwsParsing.parseAuth(request.getAuthToken(), JwsParsing.JWT_SUBJECT);
            return Future.succeededFuture(new ApiResponse<>(200, NotificationSending.list(login, request)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }
}
