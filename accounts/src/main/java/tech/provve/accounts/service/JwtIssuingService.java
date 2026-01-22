package tech.provve.accounts.service;

/**
 * Инфрастрктурный сервис для создания JWT
 */
public interface JwtIssuingService {

    /**
     * Issue new token for an account.
     *
     * @param login   login of an account
     * @param premium is account has premium access?
     * @return JWT
     */
    String issue(String login, boolean premium);

}
