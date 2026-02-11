package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AuthenticateUser200Response;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.DeleteAccountRequest;
import tech.provve.api.server.generated.dto.RegisterAccountRequest;
import tech.provve.api.server.generated.dto.UpdateAvatarRequest;
import tech.provve.api.server.generated.dto.UpdateEmailRequest;
import tech.provve.api.server.generated.dto.UpdatePasswordRequest;
import tech.provve.api.server.generated.dto.UpdatePersonalDataConsentRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

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
