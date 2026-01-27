package tech.provve.accounts.service;

/**
 * Инфрастрктурный сервис для создания JWT
 */
public interface JwtIssuingService {

    /**
     * Issue new authentication token.
     *
     * @param login   login of an account
     * @param premium is account has premium access?
     * @return JWT
     */
    String issueAuth(String login, boolean premium);

    /**
     * Issue new reset token.
     *
     * @param login the account for what reset token is issuing
     * @return JWT
     */
    String issueReset(String login);

}
