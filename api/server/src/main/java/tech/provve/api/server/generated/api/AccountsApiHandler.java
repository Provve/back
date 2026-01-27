package tech.provve.api.server.generated.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.RouteHandler;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.RegisterUserRequest;
import tech.provve.api.server.generated.dto.UpdateEmailRequest;
import tech.provve.api.server.generated.dto.UpdatePasswordRequest;

@Singleton
public class AccountsApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccountsApiHandler.class);

    private final AccountsApi api;

    public AccountsApiHandler(AccountsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("authenticateUser")
               .handler(this::authenticateUser);
        builder.operation("registerUser")
               .handler(this::registerUser);
        builder.operation("requestResetCode")
               .handler(this::requestResetCode);
        builder.operation("updateAvatar")
               .handler(this::updateAvatar);
        builder.operation("updateEmail")
               .handler(this::updateEmail);
        builder.operation("updatePassword")
               .handler(this::updatePassword);
        builder.operation("upgradeAccount")
               .handler(this::upgradeAccount);
    }

        private void authenticateUser(RoutingContext routingContext) {
            logger.info("authenticateUser()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                RequestParameter body = requestParameters.body();
            AuthenticateUserRequest authenticateUserRequest = body != null ? DatabindCodec.mapper()
                                                                                          .convertValue(
                                                                                                  body.get(),
                                                                                                  new TypeReference<AuthenticateUserRequest>() {
                                                                                                  }
                                                                                          ) : null;

            logger.debug("Parameter authenticateUserRequest is {}", authenticateUserRequest);

            api.authenticateUser(authenticateUserRequest)
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

        private void registerUser(RoutingContext routingContext) {
            logger.info("registerUser()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                RequestParameter body = requestParameters.body();
            RegisterUserRequest registerUserRequest = body != null ? DatabindCodec.mapper()
                                                                                  .convertValue(
                                                                                          body.get(),
                                                                                          new TypeReference<RegisterUserRequest>() {
                                                                                          }
                                                                                  ) : null;

            logger.debug("Parameter registerUserRequest is {}", registerUserRequest);

            api.registerUser(registerUserRequest)
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

        private void requestResetCode(RoutingContext routingContext) {
            logger.info("requestResetCode()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

            String email = requestParameters.queryParameter("email") != null ? requestParameters.queryParameter("email")
                                                                                                .getString() : null;

            logger.debug("Parameter email is {}", email);

            api.requestResetCode(email)
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

        private void updateAvatar(RoutingContext routingContext) {
            logger.info("updateAvatar()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

            FileUpload avatar = routingContext.fileUploads()
                                              .iterator()
                                              .next();

            logger.debug("Parameter avatar is {}", avatar);

            api.updateAvatar(avatar)
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

        private void updateEmail(RoutingContext routingContext) {
            logger.info("updateEmail()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                RequestParameter body = requestParameters.body();
            UpdateEmailRequest updateEmailRequest = body != null ? DatabindCodec.mapper()
                                                                                .convertValue(
                                                                                        body.get(),
                                                                                        new TypeReference<UpdateEmailRequest>() {
                                                                                        }
                                                                                ) : null;

            logger.debug("Parameter updateEmailRequest is {}", updateEmailRequest);

            api.updateEmail(updateEmailRequest)
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

        private void updatePassword(RoutingContext routingContext) {
            logger.info("updatePassword()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                RequestParameter body = requestParameters.body();
            UpdatePasswordRequest updatePasswordRequest = body != null ? DatabindCodec.mapper()
                                                                                      .convertValue(
                                                                                              body.get(),
                                                                                              new TypeReference<UpdatePasswordRequest>() {
                                                                                              }
                                                                                      ) : null;

            logger.debug("Parameter updatePasswordRequest is {}", updatePasswordRequest);

            api.updatePassword(updatePasswordRequest)
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

        private void upgradeAccount(RoutingContext routingContext) {
            logger.info("upgradeAccount()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);


            api.upgradeAccount()
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
