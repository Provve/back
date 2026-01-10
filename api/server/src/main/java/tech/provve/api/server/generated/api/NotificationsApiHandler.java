package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;

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

import java.util.List;
import java.util.Map;

public class NotificationsApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsApiHandler.class);

    private final NotificationsApi api;

    public NotificationsApiHandler(NotificationsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("notificationsDelete")
               .handler(this::notificationsDelete);
        builder.operation("notificationsGet")
               .handler(this::notificationsGet);
    }

    private void notificationsDelete(RoutingContext routingContext) {
        logger.info("notificationsDelete()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);


        api.notificationsDelete()
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

    private void notificationsGet(RoutingContext routingContext) {
        logger.info("notificationsGet()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        Pagination pagination = requestParameters.queryParameter("pagination") != null ? DatabindCodec.mapper()
                                                                                                      .convertValue(
                                                                                                              requestParameters.queryParameter(
                                                                                                                                       "pagination")
                                                                                                                               .get(),
                                                                                                              new TypeReference<Pagination>() {
                                                                                                              }
                                                                                                      ) : null;

        logger.debug("Parameter pagination is {}", pagination);

        api.notificationsGet(pagination)
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
