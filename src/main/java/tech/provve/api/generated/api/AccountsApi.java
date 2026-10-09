package tech.provve.api.generated.api;

import io.vertx.core.Future;
import tech.provve.api.ApiResponse;
import tech.provve.api.generated.dto.*;

public interface AccountsApi {
    Future<ApiResponse<AuthenticateUserResponse>> authenticateUser(AuthenticateUserRequest authenticateUserRequest);
    Future<ApiResponse<Void>> deleteAccount(DeleteAccountRequest deleteAccountRequest);
    Future<ApiResponse<Void>> registerAccount(RegisterAccountRequest registerAccountRequest);
    Future<ApiResponse<Void>> requestResetCode(String email);
    Future<ApiResponse<Void>> updateAvatar(UpdateAvatarRequest updateAvatarRequest);
    Future<ApiResponse<Void>> updateContacts(UpdateContactsRequest updateContactsRequest);
    Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest);
    Future<ApiResponse<Void>> updateInterests(UpdateInterestsRequest updateInterestsRequest);
    Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    Future<ApiResponse<Void>> updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest);
    Future<ApiResponse<String>> upgradeAccount(String login);
    Future<ApiResponse<ProfilePrivateView>> viewProfile(ViewPrivateProfile viewPrivateProfile);
}
