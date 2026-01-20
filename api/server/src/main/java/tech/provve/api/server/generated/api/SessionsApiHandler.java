package tech.provve.api.server.generated.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.RouteHandler;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.ObservationUpload;

@Singleton
public class SessionsApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(SessionsApiHandler.class);

    private final SessionsApi api;

    public SessionsApiHandler(SessionsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("createSession")
               .handler(this::createSession);
        builder.operation("getRandomValueForAntifraud")
               .handler(this::getRandomValueForAntifraud);
        builder.operation("uploadObservation")
               .handler(this::uploadObservation);
    }

    private void createSession(RoutingContext routingContext) {
        logger.info("createSession()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        CreateSessionRequest createSessionRequest = body != null ? DatabindCodec.mapper()
                                                                                .convertValue(
                                                                                        body.get(),
                                                                                        new TypeReference<CreateSessionRequest>() {
                                                                                        }
                                                                                ) : null;

        logger.debug("Parameter createSessionRequest is {}", createSessionRequest);

        api.createSession(createSessionRequest)
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

    private void getRandomValueForAntifraud(RoutingContext routingContext) {
        logger.info("getRandomValueForAntifraud()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);


        api.getRandomValueForAntifraud()
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

    private void uploadObservation(RoutingContext routingContext) {
        logger.info("uploadObservation()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        ObservationUpload observationUpload = body != null ? DatabindCodec.mapper()
                                                                          .convertValue(
                                                                                  body.get(),
                                                                                  new TypeReference<ObservationUpload>() {
                                                                                  }
                                                                          ) : null;

        logger.debug("Parameter observationUpload is {}", observationUpload);

        api.uploadObservation(observationUpload)
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
