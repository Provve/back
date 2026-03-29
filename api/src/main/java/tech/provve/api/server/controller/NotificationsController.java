package tech.provve.api.server.controller;

import io.vertx.core.Future;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.NotificationsApi;
import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.Notifications;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.notification.service.NotificationSendingService;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class NotificationsController implements NotificationsApi {

    private final InputValidator inputValidator;
    private final JwsParsingService jwsParsingService;
    private final NotificationSendingService notificationSendingService;

    @Override
    public Future<ApiResponse<Void>> clearNotifications() {
        return null;
    }

    @Override
    public Future<ApiResponse<Notifications>> listNotifications(CollectionAuthenticatedRequest request) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(request));
            var login = jwsParsingService.parseAuth(request.getAuthToken(), JwsParsingService.JWT_SUBJECT);
            return Future.succeededFuture(new ApiResponse<>(200, notificationSendingService.list(login, request)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }
}
