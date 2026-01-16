package tech.provve.accounts.service.application;

import tech.provve.api.server.generated.dto.RegisterUserRequest;

/**
 * Прикладной сервис управления базой аккаунтов.
 */
public interface Accounts {

    void registerUser(RegisterUserRequest registerUserRequest);

}
