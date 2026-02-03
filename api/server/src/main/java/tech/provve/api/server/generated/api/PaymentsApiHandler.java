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
import tech.provve.api.server.generated.dto.RobokassaConfirmPaymentRequest;

@Singleton
public class PaymentsApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(PaymentsApiHandler.class);

    private final PaymentsApi api;

    public PaymentsApiHandler(PaymentsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("confirmPayment")
               .handler(this::notifySuccessfulPayment);
    }

    private void notifySuccessfulPayment(RoutingContext routingContext) {
        logger.info("notifySuccessfulPayment()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest = body != null ? DatabindCodec.mapper()
                                                                                                    .convertValue(
                                                                                                            body.get(),
                                                                                                            new TypeReference<RobokassaConfirmPaymentRequest>() {
                                                                                                            }
                                                                                                    ) : null;

        logger.debug("Parameter robokassaConfirmPaymentRequest is {}", robokassaConfirmPaymentRequest);

        api.confirmPayment(robokassaConfirmPaymentRequest)
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
