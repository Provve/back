package tech.provve.api.server.controller;

import io.avaje.inject.External;
import io.vertx.core.Future;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.exception.*;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.AccountsApi;
import tech.provve.api.server.generated.dto.*;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.api.server.validation.dto.RequestResetCode;
import tech.provve.payment.exception.PaymentGatewayNotAccessible;
import tech.provve.payment.service.application.PaymentService;

@Singleton
@RequiredArgsConstructor
public class AccountsController implements AccountsApi {

    private final AccountService accountService;
    private final InputValidator validatingService;

    @External
    private final PaymentService paymentService;

    public Future<ApiResponse<AuthenticateUser200Response>> authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(authenticateUserRequest));

            var token = accountService.authenticate(authenticateUserRequest);
            return Future.succeededFuture(new ApiResponse<>(
                    new AuthenticateUser200Response(token)
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
            validatingService.validate(InputValidatorMapper.INSTANCE.map(registerAccountRequest));
            accountService.register(registerAccountRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AccountAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(deleteAccountRequest));
            accountService.delete(deleteAccountRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    public Future<ApiResponse<Void>> requestResetCode(String email) {
        try {
            validatingService.validate(new RequestResetCode(email));
            accountService.requestResetCode(email);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

    public Future<ApiResponse<Void>> updateAvatar(UpdateAvatarRequest updateAvatarRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(updateAvatarRequest));
            accountService.updateAvatar(updateAvatarRequest);
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
        accountService.updateContacts(updateContactsRequest);
        return Future.succeededFuture(new ApiResponse<>(200));
    }

    public Future<ApiResponse<Void>> updateEmail(UpdateEmailRequest updateEmailRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(updateEmailRequest));
            accountService.updateEmail(updateEmailRequest);
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
            validatingService.validate(InputValidatorMapper.INSTANCE.map(updateInterestsRequest));
            accountService.updateInterests(updateInterestsRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    public Future<ApiResponse<Void>> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(updatePasswordRequest));
            accountService.updatePassword(updatePasswordRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(updatePersonalDataConsentRequest));
            accountService.updatePersonalDataConsent(updatePersonalDataConsentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<String>> upgradeAccount(String login) {
        try {
            return Future.succeededFuture(new ApiResponse<>(paymentService.createInvoice(login)));
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
            return Future.succeededFuture(new ApiResponse<>(200, accountService.viewPrivateProfile(viewPrivateProfile)));
        } catch (AccessDenied e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (AccountNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        }
    }

}
