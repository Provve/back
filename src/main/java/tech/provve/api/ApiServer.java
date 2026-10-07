package tech.provve.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilderOptions;
import lombok.extern.slf4j.Slf4j;
import tech.provve.api.controller.*;
import tech.provve.api.generated.api.*;
import tech.provve.api.controller.*;
import tech.provve.api.exception.HttpException;
import tech.provve.api.generated.api.*;
import tech.provve.api.ApiRateLimiter;
import tech.provve.util.JWT;
import tech.provve.util.Storage;

import java.util.List;

@Slf4j
@SuppressWarnings("unused")
public class ApiServer extends AbstractVerticle {

    public static Vertx vertx;

    public static final String JWT_PROVIDER_AUTH = "auth";
    public static final String JWT_HANDLER_AUTH = "auth-handler";

    public static final String JWT_PROVIDER_RESET = "reset";
    public static final String JWT_HANDLER_RESET = "reset-handler";

    public static final String JWT_PROVIDER_TRUST = "trust";
    public static final String JWT_HANDLER_TRUST = "trust-handler";

    public static final int PORT = 8080;
    private static final String SPEC_FILE = "provve-api.yaml";

    private final List<RouteHandler> handlers = List.of(
            new AccountsApiHandler(new AccountsController()),
            new NotificationsApiHandler(new NotificationsController()),
            new PaymentsApiHandler(new PaymentsController()),
            new SessionsApiHandler(new SessionsController()),
            new SkillsApiHandler(new SkillsController()),
            new VotesApiHandler(new VotesController())
    );

    @Override
    public void start(Promise<Void> startPromise) {
        ApiServer.vertx = getVertx();

        RouterBuilder.create(vertx, SPEC_FILE)
                     .map(builder -> {
                         handlers.forEach(handler -> handler.mount(builder));

                         return builder.setOptions(new RouterBuilderOptions()
                                                           .setRequireSecurityHandlers(true))
                                       .securityHandler(JWT_PROVIDER_AUTH, JWT.JWT_AUTH_HANDLER)
                                       .securityHandler(JWT_PROVIDER_RESET, JWT.JWT_RESET_HANDLER)
                                       .securityHandler(JWT_PROVIDER_TRUST, JWT.JWT_TRUST_HANDLER)
                                       .createRouter();
                     })
                     .map(api -> {
                         // путь действителен?
                         api.getRoutes()
                            .stream()
                            .filter(route -> "/auth".equals(route.getName()))
                            .findFirst()
                            .orElseThrow();

                         var root = Router.router(vertx)
                                          .errorHandler(400, this::handlerStatus400)
                                          .errorHandler(500, this::handlerStatus500);
                         root.route("/v1/*")
                             .handler(ApiRateLimiter::handle)
                             .subRouter(api);

                         return root;
                     })
                     .compose(router -> vertx.createHttpServer()
                                             .requestHandler(router)
                                             .listen(PORT))
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
