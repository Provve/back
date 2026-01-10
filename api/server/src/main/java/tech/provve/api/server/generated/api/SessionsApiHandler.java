package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.ObservationPostRequest;
import tech.provve.api.server.generated.dto.SessionCreatedNotification;
import tech.provve.api.server.generated.dto.SessionStage2PostRequest;

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

public class SessionsApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(SessionsApiHandler.class);

    private final SessionsApi api;

    public SessionsApiHandler(SessionsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("observationPost")
               .handler(this::observationPost);
        builder.operation("sessionStage1Get")
               .handler(this::sessionStage1Get);
        builder.operation("sessionStage2Post")
               .handler(this::sessionStage2Post);
    }

    private void observationPost(RoutingContext routingContext) {
        logger.info("observationPost()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        ObservationPostRequest observationPostRequest = body != null ? DatabindCodec.mapper()
                                                                                    .convertValue(
                                                                                            body.get(),
                                                                                            new TypeReference<ObservationPostRequest>() {
                                                                                            }
                                                                                    ) : null;

        logger.debug("Parameter observationPostRequest is {}", observationPostRequest);

        api.observationPost(observationPostRequest)
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

    private void sessionStage1Get(RoutingContext routingContext) {
        logger.info("sessionStage1Get()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);


        api.sessionStage1Get()
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

    private void sessionStage2Post(RoutingContext routingContext) {
        logger.info("sessionStage2Post()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        SessionStage2PostRequest sessionStage2PostRequest = body != null ? DatabindCodec.mapper()
                                                                                        .convertValue(
                                                                                                body.get(),
                                                                                                new TypeReference<SessionStage2PostRequest>() {
                                                                                                }
                                                                                        ) : null;

        logger.debug("Parameter sessionStage2PostRequest is {}", sessionStage2PostRequest);

        api.sessionStage2Post(sessionStage2PostRequest)
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
