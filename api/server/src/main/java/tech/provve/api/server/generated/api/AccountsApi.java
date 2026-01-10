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

import java.util.List;
import java.util.Map;

public interface AccountsApi {

    Future<ApiResponse<Void>> accountsPost(AccountsPostRequest accountsPostRequest);

    Future<ApiResponse<AuthGet200Response>> authGet(AuthGetRequest authGetRequest);

    Future<ApiResponse<Void>> avatarPut(java.util.UUID id, FileUpload avatar);

    Future<ApiResponse<Void>> emailPut(String id, EmailPutRequest emailPutRequest);

    Future<ApiResponse<Void>> passwordPut(PasswordPutRequest passwordPutRequest);

    Future<ApiResponse<Void>> resetCodeGet(ResetCodeGetRequest resetCodeGetRequest);

    Future<ApiResponse<Void>> upgradeGet();
}
