package tech.provve.api.server;

import io.avaje.inject.BeanScope;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilderOptions;
import lombok.extern.slf4j.Slf4j;
import tech.provve.api.server.exception.HttpException;

import java.util.List;

@Slf4j
@SuppressWarnings("unused")
public class ApiServer extends AbstractVerticle {

    private static final String SPEC_FILE = "provve-api.yaml";

    private final List<RouteHandler> handlers;

    @SuppressWarnings("unused")
    public ApiServer() {
        BeanScope beanScope = BeanScope.builder()
                                       .build();
        this.handlers = beanScope.list(RouteHandler.class);
    }

    @Override
    public void start(Promise<Void> startPromise) {
        RouterBuilder.create(vertx, SPEC_FILE)
                     .map(builder -> {
                         builder.setOptions(new RouterBuilderOptions()
                                                    // For production use case, you need to enable this flag and provide the proper security handler
                                                    .setRequireSecurityHandlers(false)
                         );

                         handlers.forEach(handler -> handler.mount(builder));

                         Router router = builder.createRouter();
                         var rootRouter = builder.createRouter();

                         rootRouter.errorHandler(500, this::handlerStatus500);
                         rootRouter.errorHandler(400, this::handlerStatus400);

                         rootRouter.route("/api/v1/*")
                                   .subRouter(router);

                         return rootRouter;
                     })
                     .compose(router -> vertx.createHttpServer()
                                             .requestHandler(router)
                                             .listen(8080))
                     .onSuccess(server -> log.info("API Server started successfully"))
                     .onFailure(t -> log.error("API Server not started", t))
                     .<Void>mapEmpty()
                     .onComplete(startPromise);
    }

    private void handlerStatus500(RoutingContext rc) {
        var failure = rc.failure();
        int status = 500;

        if (failure instanceof HttpException e) {
            status = e.getStatusCode();
        }

        rc.response()
          .setStatusCode(status)
          .end("Error: " + rc.failure()
                             .getMessage()
          );
    }

    private void handlerStatus400(RoutingContext rc) {
        rc.response()
          .setStatusCode(400)
          .end("Error: " + rc.failure()
                             .getMessage()
          );
    }
}
