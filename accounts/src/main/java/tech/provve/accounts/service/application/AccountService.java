package tech.provve.accounts.service.application;

import tech.provve.accounts.exception.*;
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

    void updateEmail(UpdateEmailRequest updateEmailRequest) throws NoPersonalDataConsent;

    void updateAvatar(UpdateAvatarRequest updateAvatarRequest);

    void updateContacts(UpdateContactsRequest updateContactsRequest);

    void updateInterests(UpdateInterestsRequest request);

    void updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest);

    /**
     * Выдать премиум-статус
     */
    void upgrade(String login) throws AccountNotFound, AccountAlreadyUpgraded;

    /**
     * Убрать премиум-статус у аккаунта, чей срок подписки истек.
     */
    void downgrade(String login);

    ProfilePublicView viewPublicProfile(String login);
    ProfilePrivateView viewPrivateProfile(ViewPrivateProfile request) throws AccessDenied, AccountNotFound;
}
