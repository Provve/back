package tech.provve.accounts.service.application;

import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.RegisterUserRequest;

/**
 * Прикладной сервис управления базой аккаунтов.
 */
public interface AccountService {

    void registerUser(RegisterUserRequest registerUserRequest) throws DataNotValid, AccountAlreadyExists;

}
