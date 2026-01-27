package tech.provve.accounts.service;

import io.avaje.config.Config;
import io.avaje.inject.External;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class JwtIssuingServiceImpl implements JwtIssuingService {

    @External
    @Named("auth")
    private final JWTAuth jwtAuth;

    @External
    @Named("reset")
    private final JWTAuth jwtReset;

    @Override
    public String issueAuth(String login, boolean premium) {
        int expirationSeconds = Config.getInt("security.jwt.auth.expires-in-seconds");
        return jwtAuth.generateToken(
                JsonObject.of("premium", premium),
                new JWTOptions().setSubject(login)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }

    @Override
    public String issueReset(String login) {
        int expirationSeconds = Config.getInt("security.jwt.reset.expires-in-seconds");
        return jwtReset.generateToken(
                JsonObject.of(),
                new JWTOptions().setSubject(login)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }
}
