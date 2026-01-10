package tech.provve.api.server.generated;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.provve.api.server.generated.api.AccountsApiHandler;
import tech.provve.api.server.generated.api.AccountsApiImpl;
import tech.provve.api.server.generated.api.NotificationsApiHandler;
import tech.provve.api.server.generated.api.NotificationsApiImpl;
import tech.provve.api.server.generated.api.SessionsApiHandler;
import tech.provve.api.server.generated.api.SessionsApiImpl;
import tech.provve.api.server.generated.api.SkillsApiHandler;
import tech.provve.api.server.generated.api.SkillsApiImpl;
import tech.provve.api.server.generated.api.VotesApiHandler;
import tech.provve.api.server.generated.api.VotesApiImpl;

public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    private static final String specFile = "provve-api.yaml";


    private final AccountsApiHandler accountsHandler = new AccountsApiHandler(new AccountsApiImpl());

    private final NotificationsApiHandler notificationsHandler = new NotificationsApiHandler(new NotificationsApiImpl());

    private final SessionsApiHandler sessionsHandler = new SessionsApiHandler(new SessionsApiImpl());

    private final SkillsApiHandler skillsHandler = new SkillsApiHandler(new SkillsApiImpl());

    private final VotesApiHandler votesHandler = new VotesApiHandler(new VotesApiImpl());

    @Override
    public void start(Promise<Void> startPromise) {
        RouterBuilder.create(vertx, specFile)
                     .map(builder -> {
                         builder.setOptions(new RouterBuilderOptions()
                                                    // For production use case, you need to enable this flag and provide the proper security handler
                                                    .setRequireSecurityHandlers(false)
                         );

                         accountsHandler.mount(builder);
                         notificationsHandler.mount(builder);
                         sessionsHandler.mount(builder);
                         skillsHandler.mount(builder);
                         votesHandler.mount(builder);

                         Router router = builder.createRouter();
                         router.errorHandler(400, this::validationFailureHandler);

                         return router;
                     })
                     .compose(router ->
                                      vertx.createHttpServer()
                                           .requestHandler(router)
                                           .listen(8080)
                     )
                     .onSuccess(server -> logger.info("Http verticle deploy successful"))
                     .onFailure(t -> logger.error("Http verticle failed to deploy", t))
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
