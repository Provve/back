package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AuthenticateUser200Response;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.RegisterUserRequest;
import tech.provve.api.server.generated.dto.UpdateEmailRequest;
import tech.provve.api.server.generated.dto.UpdatePasswordRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.HttpException;

import java.util.List;
import java.util.Map;

// Implement this class

public class AccountsApiImpl implements AccountsApi {

    public Future<ApiResponse<AuthenticateUser200Response>> authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        return Future.failedFuture(new HttpException(501)); // todo call application service
    }

    public Future<ApiResponse<Void>> registerUser(RegisterUserRequest registerUserRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> requestResetCode(String email) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> updateAvatar(FileUpload avatar) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> upgradeAccount() {
        return Future.failedFuture(new HttpException(501));
    }

}
