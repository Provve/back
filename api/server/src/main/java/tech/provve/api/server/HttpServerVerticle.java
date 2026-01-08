package tech.provve.api.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    private static final String SPEC_FILE = "provve-api.yaml";


    @Override
    public void start(Promise<Void> startPromise) {
        RouterBuilder.create(vertx, SPEC_FILE)
                     .map(builder -> {
                         builder.setOptions(new RouterBuilderOptions()
                                                    // For production use case, you need to enable this flag and provide the proper security handler
                                                    .setRequireSecurityHandlers(false)
                         );


                         Router router = builder.createRouter();
                         router.errorHandler(400, this::validationFailureHandler);

                         return router;
                     })
                     .compose(router ->
                                      vertx.createHttpServer()
                                           .requestHandler(router)
                                           .listen(8080)
                     )
                     .onSuccess(server -> LOGGER.info("Http verticle deploy successful"))
                     .onFailure(t -> LOGGER.error("Http verticle failed to deploy", t))
                     // Complete the start promise
                     .<Void>mapEmpty()
                     .onComplete(startPromise);
    }

    private void validationFailureHandler(RoutingContext rc) {
        rc.response()
          .setStatusCode(400)
          .end("Bad Request : " + rc.failure()
                                    .getMessage());
    }
}
