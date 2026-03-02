package tech.provve.accounts.service;

public interface JwsParsingService {

    String JWT_SUBJECT = "sub";

    /**
     * Parse Reset Token generated in {@link JwtIssuingService}
     *
     * @return the attribute`s value
     */
    <T> T parseReset(String jws, T attribute);

    /**
     * Parse Auth Token generated in {@link JwtIssuingService}
     *
     * @return the attribute`s value
     */
    <T> T parseAuth(String jws, T attribute);

}
