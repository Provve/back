package tech.provve.api.controller;

import alekseyvideman.dop.Collection;
import io.vertx.core.Future;
import tech.provve.accounts.exception.*;
import tech.provve.accounts.Account;
import tech.provve.api.ApiResponse;
import tech.provve.api.exception.HttpException;
import tech.provve.api.exception.ValidationError;
import tech.provve.api.generated.api.AccountsApi;
import tech.provve.api.generated.dto.*;
import tech.provve.constants.Entity;
import tech.provve.payment.exception.PaymentGatewayNotAccessible;
import tech.provve.payment.Payment;
import tech.provve.util.Storage;
import tech.provve.util.Jackson;
import tech.provve.util.Validation;

import java.util.Map;

public class AccountsController implements AccountsApi {

    public Future<ApiResponse<AuthenticateUserResponse>> authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(authenticateUserRequest);

            String failureMessage = Validation.validateAuthenticateUserRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            var token = Account.authenticate(authenticateUserRequest);
            return Future.succeededFuture(new ApiResponse<>(
                    new AuthenticateUserResponse(token)
            ));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AccessDenied e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

    @Override
    public Future<ApiResponse<Void>> registerAccount(RegisterAccountRequest registerAccountRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(registerAccountRequest);

            String failureMessage = Validation.validateRegisterAccountRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            if (Boolean.FALSE.equals(registerAccountRequest.getConsentPersonalData())) {
                throw new ValidationError("consentPersonalData if false");
            }

            Account.register(registerAccountRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AccountAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    private boolean isBlank(final String string) {
        return string == null || string.trim()
                                       .isEmpty();
    }

    @Override
    public Future<ApiResponse<Void>> deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(deleteAccountRequest);

            String failureMessage = Validation.validateDeleteAccountRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Account.delete(deleteAccountRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    public Future<ApiResponse<Void>> requestResetCode(String email) {
        try {
            Account.requestResetCode(email);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

    public Future<ApiResponse<Void>> updateAvatar(UpdateAvatarRequest updateAvatarRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(updateAvatarRequest);

            String failureMessage = Validation.validateUpdateAvatarRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Account.updateAvatar(updateAvatarRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (NoPersonalDataConsent e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (DataNotUnique e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> updateContacts(UpdateContactsRequest updateContactsRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(updateContactsRequest);

            Account.updateContacts(updateContactsRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    public Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(updateEmailRequest);

            String failureMessage = Validation.validateUpdateEmailRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Account.updateEmail(updateEmailRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (NoPersonalDataConsent e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (DataNotUnique e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> updateInterests(UpdateInterestsRequest updateInterestsRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(updateInterestsRequest);

            String failureMessage = Validation.validateUpdateInterestsRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Account.updateInterests(updateInterestsRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    public Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(updatePasswordRequest);

            String failureMessage = Validation.validateUpdatePasswordRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Account.updatePassword(updatePasswordRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(updatePersonalDataConsentRequest);

            String failureMessage = Validation.validateUpdatePersonalDataConsentRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Account.updatePersonalDataConsent(updatePersonalDataConsentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<String>> upgradeAccount(String login) {
        try {
            Storage.findAccountByLogin(login)
                   .ifPresentOrElse(
                           a -> {
                               if (Boolean.TRUE.equals(Collection.get(a, Entity.Account.IS_PREMIUM))) {
                                   throw new AccountAlreadyUpgraded("Account with login '%s' already upgraded".formatted(
                                           login));
                               }
                           }, () -> {
                               throw new AccountNotFound("Account with login '%s' not found".formatted(
                                       login));
                           }
                   );

            return Future.succeededFuture(new ApiResponse<>(Payment.createInvoice(login)));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        } catch (AccountAlreadyUpgraded e) {
            return Future.failedFuture(new HttpException(e, 409));
        } catch (PaymentGatewayNotAccessible e) {
            return Future.failedFuture(new HttpException(e, 504));
        }
    }

    @Override
    public Future<ApiResponse<ProfilePrivateView>> viewProfile(ViewPrivateProfile viewPrivateProfile) {
        try {
            return Future.succeededFuture(new ApiResponse<>(200, Account.viewPrivateProfile(viewPrivateProfile)));
        } catch (AccessDenied e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

}
