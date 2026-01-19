package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface AccountsApi {
    Future<ApiResponse<AuthenticateUser200Response>> authenticateUser(AuthenticateUserRequest authenticateUserRequest);
    Future<ApiResponse<Void>> registerUser(RegisterUserRequest registerUserRequest);
    Future<ApiResponse<Void>> requestResetCode(String email);
    Future<ApiResponse<Void>> updateAvatar(FileUpload avatar);
    Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest);
    Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    Future<ApiResponse<Void>> upgradeAccount();
}
