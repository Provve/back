package tech.provve.api.server.generated.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.generated.dto.*;

import java.util.UUID;

public class AccountsApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccountsApiHandler.class);

    private final AccountsApi api;

    public AccountsApiHandler(AccountsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("accountsPost")
               .handler(this::accountsPost);
        builder.operation("authGet")
               .handler(this::authGet);
        builder.operation("avatarPut")
               .handler(this::avatarPut);
        builder.operation("emailPut")
               .handler(this::emailPut);
        builder.operation("passwordPut")
               .handler(this::passwordPut);
        builder.operation("resetCodeGet")
               .handler(this::resetCodeGet);
        builder.operation("upgradeGet")
               .handler(this::upgradeGet);
    }

    private void accountsPost(RoutingContext routingContext) {
        logger.info("accountsPost()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        AccountsPostRequest accountsPostRequest = body != null ? DatabindCodec.mapper()
                                                                              .convertValue(
                                                                                      body.get(),
                                                                                      new TypeReference<AccountsPostRequest>() {
                                                                                      }
                                                                              ) : null;

        logger.debug("Parameter accountsPostRequest is {}", accountsPostRequest);

        api.accountsPost(accountsPostRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void authGet(RoutingContext routingContext) {
        logger.info("authGet()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        AuthGetRequest authGetRequest = body != null ? DatabindCodec.mapper()
                                                                    .convertValue(
                                                                            body.get(),
                                                                            new TypeReference<AuthGetRequest>() {
                                                                            }
                                                                    ) : null;

        logger.debug("Parameter authGetRequest is {}", authGetRequest);

        api.authGet(authGetRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void avatarPut(RoutingContext routingContext) {
        logger.info("avatarPut()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID id = requestParameters.pathParameter("id") != null
                ? UUID.fromString(requestParameters.pathParameter("id")
                                                   .getString()) : null;
        FileUpload avatar = routingContext.fileUploads()
                                          .iterator()
                                          .next();

        logger.debug("Parameter id is {}", id);
        logger.debug("Parameter avatar is {}", avatar);

        api.avatarPut(id, avatar)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void emailPut(RoutingContext routingContext) {
        logger.info("emailPut()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String id = requestParameters.pathParameter("id") != null ? requestParameters.pathParameter("id")
                                                                                     .getString() : null;
        RequestParameter body = requestParameters.body();
        EmailPutRequest emailPutRequest = body != null ? DatabindCodec.mapper()
                                                                      .convertValue(
                                                                              body.get(),
                                                                              new TypeReference<EmailPutRequest>() {
                                                                              }
                                                                      ) : null;

        logger.debug("Parameter id is {}", id);
        logger.debug("Parameter emailPutRequest is {}", emailPutRequest);

        api.emailPut(id, emailPutRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void passwordPut(RoutingContext routingContext) {
        logger.info("passwordPut()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        PasswordPutRequest passwordPutRequest = body != null ? DatabindCodec.mapper()
                                                                            .convertValue(
                                                                                    body.get(),
                                                                                    new TypeReference<PasswordPutRequest>() {
                                                                                    }
                                                                            ) : null;

        logger.debug("Parameter passwordPutRequest is {}", passwordPutRequest);

        api.passwordPut(passwordPutRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void resetCodeGet(RoutingContext routingContext) {
        logger.info("resetCodeGet()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        ResetCodeGetRequest resetCodeGetRequest = body != null ? DatabindCodec.mapper()
                                                                              .convertValue(
                                                                                      body.get(),
                                                                                      new TypeReference<ResetCodeGetRequest>() {
                                                                                      }
                                                                              ) : null;

        logger.debug("Parameter resetCodeGetRequest is {}", resetCodeGetRequest);

        api.resetCodeGet(resetCodeGetRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void upgradeGet(RoutingContext routingContext) {
        logger.info("upgradeGet()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);


        api.upgradeGet()
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

}
