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
        var key = Keys.hmacShaKeyFor(Config.get("security.jwt.reset.secret")
                                           .getBytes());
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(jws)
                   .getPayload();
    }
}
