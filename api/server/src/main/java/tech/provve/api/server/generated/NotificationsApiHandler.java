package tech.provve.api.server.generated;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.generated.dto.Pagination;

public class NotificationsApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsApiHandler.class);

    private final NotificationsApi api;

    @Deprecated
    public NotificationsApiHandler() {
        this(new NotificationsApiImpl());
    }

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
