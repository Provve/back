package tech.provve.accounts.service.application;

import tech.provve.accounts.exception.AccessDenied;
import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.AccountAlreadyUpgraded;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.api.server.generated.dto.*;

/**
 * Прикладной сервис управления аккаунтами.
 */
public interface AccountService {

    void register(RegisterAccountRequest registerAccountRequest) throws AccountAlreadyExists;

    /**
     * Delete all account`s data
     */
    void delete(DeleteAccountRequest deleteAccountRequest);

    /**
     * @return JWT
     */
    String authenticate(AuthenticateUserRequest authenticateUserRequest) throws AccountNotFound, AccessDenied;

    /**
     *
     * @param email of owner to send the code
     */
    void requestResetCode(String email);

    void updatePassword(UpdatePasswordRequest updatePasswordRequest);

    void updateEmail(UpdateEmailRequest updateEmailRequest);

    void updateAvatar(UpdateAvatarRequest updateAvatarRequest);

    void updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest);

    /**
     * Выдать премиум-статус
     */
    void upgrade(String login) throws AccountNotFound, AccountAlreadyUpgraded;

    /**
     * Убрать премиум-статус у всех аккаунтов, чей срок подписки истек.
     */
    void downgradeAllExpired();

}
