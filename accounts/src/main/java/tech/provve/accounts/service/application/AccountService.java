package tech.provve.accounts.service.application;

import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

/**
 * Прикладной сервис управления базой аккаунтов.
 */
public interface AccountService {

    void register(RegisterUserRequest registerUserRequest) throws DataNotValid, AccountAlreadyExists;

    /**
     * @return JWT
     */
    String authenticate(AuthenticateUserRequest authenticateUserRequest) throws DataNotValid, AccountNotFound;

}
