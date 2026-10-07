package tech.provve.accounts;

import io.avaje.config.Config;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;

import static tech.provve.accounts.JwsParsing.PREMIUM;
import static tech.provve.util.JWT.JWT_AUTH;
import static tech.provve.util.JWT.JWT_RESET;

/**
 * Инфрастрктурный сервис для создания JWT для аккаунта
 */
public class JwtIssuing {

    /**
     * Issue new authentication token.
     *
     * @param login   login of an account
     * @param premium is account has premium access?
     * @return JWT
     */
    public static String issueAuth(String login, boolean premium) {
        int expirationSeconds = Config.getInt("security.jwt.auth.expires-in-seconds");
        return JWT_AUTH.generateToken(
                JsonObject.of(PREMIUM, premium),
                new JWTOptions().setSubject(login)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }

    /**
     * Issue new reset token.
     *
     * @param login the account for what reset token is issuing
     * @return JWT
     */
    public static String issueReset(String login) {
        int expirationSeconds = Config.getInt("security.jwt.reset.expires-in-seconds");
        return JWT_RESET.generateToken(
                new JsonObject(),
                new JWTOptions().setSubject(login)
                                .setExpiresInSeconds(expirationSeconds)
        );
    }
}
