package tech.provve.accounts.service;

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
    public Map<String, Object> parseReset(String jws) {
        return parse(
                jws, Config.get("security.jwt.reset.secret")
                           .getBytes()
        );
    }

    @Override
    public Map<String, Object> parseAuth(String jws) {
        return parse(
                jws, Config.get("security.jwt.auth.secret")
                           .getBytes()
        );
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
