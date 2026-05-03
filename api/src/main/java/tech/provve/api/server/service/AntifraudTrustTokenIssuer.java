package tech.provve.api.server.service;

import io.avaje.config.Config;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import static tech.provve.api.server.factory.Security.JWT_PROVIDER_TRUST;

@Singleton
@RequiredArgsConstructor
public class AntifraudTrustTokenIssuer {

    @Named(JWT_PROVIDER_TRUST)
    private final JWTAuth jwtAuth;

    /**
     * @return JWT token for trusted Antifraud
     */
    public String trust(String examinee) {
        int expirationSeconds = Config.getInt("antifraud.trust-token.expires-in-seconds");
        return jwtAuth.generateToken(
                new JsonObject(),
                new JWTOptions().setSubject(examinee)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }
}
