package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.*;

// Implement this class

public class AccountsApiImpl implements AccountsApi {

    public Future<ApiResponse<Void>> accountsPost(AccountsPostRequest accountsPostRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<AuthGet200Response>> authGet(AuthGetRequest authGetRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> avatarPut(java.util.UUID id, FileUpload avatar) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> emailPut(String id, EmailPutRequest emailPutRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> passwordPut(PasswordPutRequest passwordPutRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> resetCodeGet(ResetCodeGetRequest resetCodeGetRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> upgradeGet() {
        return Future.failedFuture(new HttpException(501));
    }

}
