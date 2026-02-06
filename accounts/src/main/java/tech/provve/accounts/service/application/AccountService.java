package tech.provve.accounts.service.application;

import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.AccountAlreadyUpgraded;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.api.server.generated.dto.*;

/**
 * Прикладной сервис управления базой аккаунтов.
 */
public interface AccountService {

    void register(RegisterUserRequest registerUserRequest) throws DataNotValid, AccountAlreadyExists;

    /**
     * @return JWT
     */
    String authenticate(AuthenticateUserRequest authenticateUserRequest) throws DataNotValid, AccountNotFound;

    /**
     *
     * @param email of owner to send the code
     */
    void requestResetCode(String email);

    void updatePassword(UpdatePasswordRequest updatePasswordRequest);

    void updateEmail(UpdateEmailRequest updateEmailRequest);

    void updateAvatar(UpdateAvatarRequest updateAvatarRequest);

    /**
     * Выдать премиум-статус
     */
    void upgrade(String login) throws AccountNotFound, AccountAlreadyUpgraded;

    /**
     * Убрать премиум-статус у всех аккаунтов, чей срок подписки истек.
     */
    void downgradeAllExpired();

}
