package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.Error;
import tech.provve.api.server.generated.dto.Notifications;

import tech.provve.api.server.RouteHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;

@Singleton
public class NotificationsApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsApiHandler.class);

    private final NotificationsApi api;

    public NotificationsApiHandler(NotificationsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("clearNotifications")
               .handler(this::clearNotifications);
        builder.operation("listNotifications")
               .handler(this::listNotifications);
    }

    private void clearNotifications(RoutingContext routingContext) {
        logger.info("clearNotifications()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);


        api.clearNotifications()
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void listNotifications(RoutingContext routingContext) {
        logger.info("listNotifications()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        CollectionAuthenticatedRequest collectionAuthenticatedRequest = body != null
                                                                        ? DatabindCodec.mapper()
                                                                                       .convertValue(body.get(),
                                                                                                     new TypeReference<CollectionAuthenticatedRequest>() {
                                                                                                     })
                                                                        : null;

        logger.debug("Parameter collectionAuthenticatedRequest is {}", collectionAuthenticatedRequest);

        api.listNotifications(collectionAuthenticatedRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

}
