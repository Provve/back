package tech.provve.accounts.service;

import io.avaje.config.Config;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import jakarta.inject.Singleton;

@Singleton
public class JwtIssuingServiceImpl implements JwtIssuingService {

    private final JWTAuth jwtAuth;

    public JwtIssuingServiceImpl(JWTAuthOptions options) {
        jwtAuth = JWTAuth.create(Vertx.vertx(), options);
    }

    @Override
    public String issue(String login, boolean premium) {
        int expirationSeconds = Config.getInt("security.jwt.expires-in-seconds");
        return jwtAuth.generateToken(
                JsonObject.of("premium", premium),
                new JWTOptions().setSubject(login)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }
}
