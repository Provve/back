package tech.provve.libs.auth;

import io.avaje.config.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class JwsParsingServiceImpl implements JwsParsingService {

    @Override
    public <T> T parseReset(String jws, T attribute) {
        var jwtPayload = parse(
                jws, Config.get("security.jwt.reset.secret")
                           .getBytes()
        );
        return ((T) jwtPayload.get(attribute));
    }

    @Override
    public <T> T parseAuth(String jws, T attribute) {
        var jwtPayload = parse(
                jws, Config.get("security.jwt.auth.secret")
                           .getBytes()
        );
        return ((T) jwtPayload.get(attribute));
    }

    @Override
    public <T> T parseTrust(String jws, T attribute) {
        var jwtPayload = parse(
                jws, Config.get("antifraud.trust-token.secret")
                           .getBytes()
        );
        return ((T) jwtPayload.get(attribute));
    }

    private Map<String, Object> parse(String jws, byte[] secret) {
        var key = Keys.hmacShaKeyFor(secret);
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(jws)
                   .getPayload();
    }
}
