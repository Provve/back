package tech.provve.api.server.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import jakarta.inject.Named;

@Factory
public class Security {

    @Bean
    @Named("auth")
    public JWTAuth jwtAuth(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.auth.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

    @Bean
    @Named("reset")
    public JWTAuth jwtReset(@External Vertx vertx) {
        var options = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.reset.secret"))
                );
        return JWTAuth.create(vertx, options);
    }

}
