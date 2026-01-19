package tech.provve.accounts.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtIssuingServiceImpl implements JwtIssuingService {

    @Override
    public String issue(Object user_data) {
        Key hmacKey = new SecretKeySpec(
                "TOP_LOCAL_SECRET".getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );

//        log.info("Token was generated for {}", username);
        var expirationDate = Date.from(Instant.now()
                                              .plus(500, ChronoUnit.SECONDS));

        return Jwts.builder()
                   .setSubject("")
                   .setId("")
                   .setExpiration(expirationDate)
                   .signWith(hmacKey)
                   .compact();
    }
}
