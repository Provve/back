package tech.provve.accounts;

import io.avaje.config.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Map;

public class JwsParsing {

    public static final String JWT_SUBJECT = "sub";
    public static final String PREMIUM = "premium";

    /**
     * Parse Reset Token generated in {@link JwtIssuing}
     *
     * @return the attribute`s value
     */
    public static <T> T parseReset(String jws, T attribute) {
        var jwtPayload = parse(
                jws, Config.get("security.jwt.reset.secret")
                           .getBytes()
        );
        return ((T) jwtPayload.get(attribute));
    }


    /**
     * Parse Auth Token generated in {@link JwtIssuing}
     *
     * @return the attribute`s value
     */
    public static <T> T parseAuth(String jws, T attribute) {
        var jwtPayload = parse(
                jws, Config.get("security.jwt.auth.secret")
                           .getBytes()
        );
        return ((T) jwtPayload.get(attribute));
    }


    /**
     * Parse trust token from Antifraud
     *
     * @return the attribute`s value
     */
    public static <T> T parseTrust(String jws, T attribute) {
        var jwtPayload = parse(
                jws, Config.get("antifraud.trust-token.secret")
                           .getBytes()
        );
        return ((T) jwtPayload.get(attribute));
    }

    private static Map<String, Object> parse(String jws, byte[] secret) {
        var key = Keys.hmacShaKeyFor(secret);
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(jws)
                   .getPayload();
    }
}
