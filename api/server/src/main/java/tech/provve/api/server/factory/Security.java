package tech.provve.api.server.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.JWTAuthHandler;
import jakarta.inject.Named;

@Factory
public class Security {

    public static final String JWT_PROVIDER_AUTH = "auth";

    public static final String JWT_HANDLER_AUTH = "auth-handler";

    public static final String JWT_PROVIDER_RESET = "reset";

    public static final String JWT_HANDLER_RESET = "reset-handler";

    @Bean
    @Named(JWT_HANDLER_AUTH)
    public JWTAuthHandler jwtAuthHandler(@Named(JWT_PROVIDER_AUTH) JWTAuth jwtAuth) {
        return JWTAuthHandler.create(jwtAuth);
    }

    @Bean
    @Named(JWT_HANDLER_RESET)
    public JWTAuthHandler jwtResetHandler(@Named(JWT_PROVIDER_RESET) JWTAuth jwtReset) {
        return JWTAuthHandler.create(jwtReset);
    }

    @Bean
    @Named(JWT_PROVIDER_AUTH)
    public JWTAuth jwtAuth(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.auth.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

    @Bean
    @Named(JWT_PROVIDER_RESET)
    public JWTAuth jwtReset(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.reset.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

}
