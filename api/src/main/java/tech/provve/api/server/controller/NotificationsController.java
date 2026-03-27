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
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Notifications;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.notification.repository.NotificationRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class NotificationsController implements NotificationsApi {

    private final InputValidator inputValidator;
    private final NotificationRepository notificationRepository;
    private final JwsParsingService jwsParsingService;

    @Override
    public Future<ApiResponse<Void>> clearNotifications() {
        return null;
    }

    @Override
    public Future<ApiResponse<Notifications>> listNotifications(CollectionRequest collectionRequest) {
        try {
            inputValidator.validate(InputValidatorMapper.INSTANCE.map(collectionRequest));
            var login = jwsParsingService.parseAuth(collectionRequest.getAuthToken(), JwsParsingService.JWT_SUBJECT);
            List<Notification> all = notificationRepository.findAllBy(login, collectionRequest.getPagination()
                                                                                              .getPrevious(),
                                                                      collectionRequest.getPagination()
                                                                                       .getSize())
                                                           .stream()
                                                           .toList();
            var cursor = new Cursor(String.valueOf(all.getLast()
                                                      .getId()));
            return Future.succeededFuture(new ApiResponse<>(200, new Notifications(all, cursor)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }
}
