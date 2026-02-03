package tech.provve.accounts.service;

import java.util.Map;

public interface JwsParsingService {

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

}
