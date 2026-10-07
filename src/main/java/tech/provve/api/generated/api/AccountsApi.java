package tech.provve.api.generated.api;

import tech.provve.api.generated.dto.AuthenticateUserRequest;
import tech.provve.api.generated.dto.AuthenticateUserResponse;
import tech.provve.api.generated.dto.DeleteAccountRequest;
import tech.provve.api.generated.dto.Error;
import tech.provve.api.generated.dto.ProfilePrivateView;
import tech.provve.api.generated.dto.RegisterAccountRequest;
import tech.provve.api.generated.dto.UpdateAvatarRequest;
import tech.provve.api.generated.dto.UpdateContactsRequest;
import tech.provve.api.generated.dto.UpdateEmailRequest;
import tech.provve.api.generated.dto.UpdateInterestsRequest;
import tech.provve.api.generated.dto.UpdatePasswordRequest;
import tech.provve.api.generated.dto.UpdatePersonalDataConsentRequest;
import tech.provve.api.generated.dto.ViewPrivateProfile;

import tech.provve.api.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

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
