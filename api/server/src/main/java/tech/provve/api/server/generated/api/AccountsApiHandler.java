package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AuthenticateUser200Response;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.generated.dto.DeleteAccountRequest;
import tech.provve.api.server.generated.dto.RegisterAccountRequest;
import tech.provve.api.server.generated.dto.UpdateAvatarRequest;
import tech.provve.api.server.generated.dto.UpdateEmailRequest;
import tech.provve.api.server.generated.dto.UpdatePasswordRequest;
import tech.provve.api.server.generated.dto.UpdatePersonalDataConsentRequest;

import tech.provve.api.server.RouteHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;

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
        builder.operation("deleteAccount")
               .handler(this::deleteAccount);
        builder.operation("registerAccount")
               .handler(this::registerAccount);
        builder.operation("requestResetCode")
               .handler(this::requestResetCode);
        builder.operation("updateAvatar")
               .handler(this::updateAvatar);
        builder.operation("updateEmail")
               .handler(this::updateEmail);
        builder.operation("updatePassword")
               .handler(this::updatePassword);
        builder.operation("updatePersonalDataConsent")
               .handler(this::updatePersonalDataConsent);
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

    private void deleteAccount(RoutingContext routingContext) {
        logger.info("deleteAccount()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        DeleteAccountRequest deleteAccountRequest = body != null ? DatabindCodec.mapper()
                                                                                .convertValue(
                                                                                        body.get(),
                                                                                        new TypeReference<DeleteAccountRequest>() {
                                                                                        }
                                                                                ) : null;

        logger.debug("Parameter deleteAccountRequest is {}", deleteAccountRequest);

        api.deleteAccount(deleteAccountRequest)
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

    private void registerAccount(RoutingContext routingContext) {
        logger.info("registerAccount()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                RequestParameter body = requestParameters.body();
        RegisterAccountRequest registerAccountRequest = body != null ? DatabindCodec.mapper()
                                                                                    .convertValue(
                                                                                            body.get(),
                                                                                            new TypeReference<RegisterAccountRequest>() {
                                                                                            }
                                                                                    ) : null;

        logger.debug("Parameter registerAccountRequest is {}", registerAccountRequest);

        api.registerAccount(registerAccountRequest)
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

            RequestParameter body = requestParameters.body();
            UpdateAvatarRequest updateAvatarRequest = body != null ? DatabindCodec.mapper()
                                                                                  .convertValue(
                                                                                          body.get(),
                                                                                          new TypeReference<UpdateAvatarRequest>() {
                                                                                          }
                                                                                  ) : null;

            logger.debug("Parameter updateAvatarRequest is {}", updateAvatarRequest);

            api.updateAvatar(updateAvatarRequest)
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

    private void updatePersonalDataConsent(RoutingContext routingContext) {
        logger.info("updatePersonalDataConsent()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest = body != null ? DatabindCodec.mapper()
                                                                                                        .convertValue(
                                                                                                                body.get(),
                                                                                                                new TypeReference<UpdatePersonalDataConsentRequest>() {
                                                                                                                }
                                                                                                        ) : null;

        logger.debug("Parameter updatePersonalDataConsentRequest is {}", updatePersonalDataConsentRequest);

        api.updatePersonalDataConsent(updatePersonalDataConsentRequest)
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

            String login = requestParameters.queryParameter("login") != null ? requestParameters.queryParameter("login")
                                                                                                .getString() : null;

            logger.debug("Parameter login is {}", login);

            api.upgradeAccount(login)
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
