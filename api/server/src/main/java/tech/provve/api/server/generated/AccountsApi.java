package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface AccountsApi {

    Future<ApiResponse<Void>> accountsPost(AccountsPostRequest accountsPostRequest);

    Future<ApiResponse<AuthGet200Response>> authGet(AuthGetRequest authGetRequest);

    Future<ApiResponse<Void>> avatarPut(java.util.UUID id, FileUpload avatar);

    Future<ApiResponse<Void>> emailPut(String id, EmailPutRequest emailPutRequest);

    Future<ApiResponse<Void>> passwordPut(PasswordPutRequest passwordPutRequest);

    Future<ApiResponse<Void>> resetCodeGet(ResetCodeGetRequest resetCodeGetRequest);

    Future<ApiResponse<Void>> upgradeGet();
}
