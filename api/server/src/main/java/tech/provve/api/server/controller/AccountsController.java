package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.exception.AccountAlreadyExists;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.exception.DataNotValid;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.AccountsApi;
import tech.provve.api.server.generated.dto.*;

@RequiredArgsConstructor
public class AccountsController implements AccountsApi {

    private final AccountService accountService;

    public Future<ApiResponse<AuthenticateUser200Response>> authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        try {
            var token = accountService.authenticate(authenticateUserRequest);
            return Future.succeededFuture(new ApiResponse<>(
                    new AuthenticateUser200Response(token)
            ));
        } catch (DataNotValid e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

    public Future<ApiResponse<Void>> registerUser(RegisterUserRequest registerUserRequest) {
        try {
            accountService.register(registerUserRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (AccountAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        } catch (DataNotValid e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    public Future<ApiResponse<Void>> requestResetCode(String email) {
        return Future.failedFuture(new HttpException(500));
    }

    public Future<ApiResponse<Void>> updateAvatar(FileUpload avatar) {
        return Future.failedFuture(new HttpException(500));
    }

    public Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest) {
        return Future.failedFuture(new HttpException(500));
    }

    public Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        return Future.failedFuture(new HttpException(500));
    }

    public Future<ApiResponse<Void>> upgradeAccount() {
        return Future.failedFuture(new HttpException(500));
    }

}
