package tech.provve.accounts.service;

/**
 * Инфрастрктурный сервис для создания JWT
 */
public interface JwtIssuingService {

    /**
     * Issue new token for the user.
     *
     * @return JWT
     */
    String issue(Object user_data);

}
