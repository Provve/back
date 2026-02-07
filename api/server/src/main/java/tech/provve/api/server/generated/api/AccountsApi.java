package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface AccountsApi {
    Future<ApiResponse<AuthenticateUser200Response>> authenticateUser(AuthenticateUserRequest authenticateUserRequest);

    Future<ApiResponse<Void>> deleteAccount(DeleteAccountRequest deleteAccountRequest);

    Future<ApiResponse<Void>> registerAccount(RegisterAccountRequest registerAccountRequest);
    Future<ApiResponse<Void>> requestResetCode(String email);
    Future<ApiResponse<Void>> updateAvatar(UpdateAvatarRequest updateAvatarRequest);
    Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest);
    Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    Future<ApiResponse<Void>> updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest);
    Future<ApiResponse<String>> upgradeAccount(String login);
}
