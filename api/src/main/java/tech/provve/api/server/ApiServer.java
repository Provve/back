package tech.provve.api.server;

import io.avaje.inject.BeanScope;
import io.avaje.inject.BeanScopeBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilderOptions;
import lombok.extern.slf4j.Slf4j;
import tech.provve.accounts.task.DowngradeExpiredPremium;
import tech.provve.accounts.task.InitS3Buckets;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.factory.Security;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static tech.provve.api.server.factory.RateLimit.RATE_LIMITER;

@Slf4j
@SuppressWarnings("unused")
public class ApiServer extends AbstractVerticle {

    public static final int PORT = 8080;

    private static final String SPEC_FILE = "provve-api.yaml";

    private static final String AUTH_SECURITY_SCHEME = "auth";

    private static final String RESET_SECURITY_SCHEME = "reset";

    private List<RouteHandler> handlers;

    private JWTAuthHandler jwtAuthHandler;

    private JWTAuthHandler jwtResetHandler;

    private ScheduledExecutorService scheduledExecutorService;

    private DowngradeExpiredPremium downgradeExpiredPremium;

    private InitS3Buckets initS3Buckets;

    private Handler<RoutingContext> rateLimiter;

    public ApiServer() {
        BeanScopeBuilder scopeBuilder = BeanScope.builder()
                                                 .bean(Vertx.class, vertx);
        init(scopeBuilder.build());
    }

    public ApiServer(BeanScopeBuilder scopeBuilder) {
        init(scopeBuilder.build());
    }

    private void init(BeanScope beanScope) {
        this.handlers = beanScope.list(RouteHandler.class);
        this.jwtAuthHandler = beanScope.get(JWTAuthHandler.class, Security.JWT_HANDLER_AUTH);
        this.jwtResetHandler = beanScope.get(JWTAuthHandler.class, Security.JWT_HANDLER_RESET);

        this.scheduledExecutorService = beanScope.get(ScheduledExecutorService.class);
        this.downgradeExpiredPremium = beanScope.get(DowngradeExpiredPremium.class);
        this.initS3Buckets = beanScope.get(InitS3Buckets.class);
        this.rateLimiter = beanScope.<Handler<RoutingContext>>get(Handler.class, RATE_LIMITER);
    }

    @Override
    public void start(Promise<Void> startPromise) {
        runTasks();

        RouterBuilder.create(vertx, SPEC_FILE)
                     .map(builder -> {
                         handlers.forEach(handler -> handler.mount(builder));

                         return builder.setOptions(new RouterBuilderOptions()
                                                           .setRequireSecurityHandlers(true))
                                       .securityHandler(AUTH_SECURITY_SCHEME, jwtAuthHandler)
                                       .securityHandler(RESET_SECURITY_SCHEME, jwtResetHandler)
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
                         root.route("/api/v1/*")
                             .handler(rateLimiter)
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

    private void runTasks() {
        scheduledExecutorService.scheduleWithFixedDelay(downgradeExpiredPremium, 0L, 30L, TimeUnit.DAYS);
        scheduledExecutorService.schedule(initS3Buckets, 0L, TimeUnit.MILLISECONDS);
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
