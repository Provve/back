package tech.provve.accounts.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

@Factory
public class Security {

    @Bean
    public JWTAuthOptions options() {
        return new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                                      .setAlgorithm("HS256")
                                      .setBuffer(Config.get("security.jwt.secret"))
                );
    }

}
