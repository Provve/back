package tech.provve.accounts.service;

import java.util.Map;

public interface JwsParsingService {

    String JWT_SUBJECT = "sub";

    /**
     * Parse Reset Token generated in {@link JwtIssuingService}
     *
     * @return payload properties
     */
    Map<String, Object> parseReset(String jws);

    /**
     * Parse Auth Token generated in {@link JwtIssuingService}
     *
     * @return payload properties
     */
    Map<String, Object> parseAuth(String jws);

    /**
     * Read certain JWT attribute
     *
     * @return the attribute`s value
     */
    String parseAuth(String jws, String attribute);

}
