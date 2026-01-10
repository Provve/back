package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AccountsPostRequest;
import tech.provve.api.server.generated.dto.AuthGet200Response;
import tech.provve.api.server.generated.dto.AuthGetRequest;
import tech.provve.api.server.generated.dto.EmailPutRequest;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.PasswordPutRequest;
import tech.provve.api.server.generated.dto.ResetCodeGetRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.HttpException;

import java.util.List;
import java.util.Map;

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
