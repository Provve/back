package tech.provve.api.server.controller;

import io.avaje.inject.External;
import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.PaymentsApi;
import tech.provve.api.server.generated.dto.RobokassaConfirmPaymentRequest;
import tech.provve.payment.gateway.robokassa.PaymentRequest;
import tech.provve.payment.service.application.PaymentService;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PaymentsController implements PaymentsApi {

    @External
    private final PaymentService paymentService;

    @External
    private final AccountService accountService;

    @External
    private final AccountRepository accountRepository;

    @Override
    public Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest) {
        var accountLogin = robokassaConfirmPaymentRequest.getShp()
                                                         .get(PaymentRequest.ACCOUNT_FIELD);
        var signature = robokassaConfirmPaymentRequest.getSignatureValue();

        boolean confirmed = paymentService.confirmPayment(accountLogin, signature);
        if (!confirmed) {
            return Future.failedFuture(new HttpException(400));
        }

        accountRepository.findByLogin(accountLogin)
                         .ifPresent(account -> accountService.upgrade(account.login()));

        int invId = robokassaConfirmPaymentRequest.getInvId();
        return Future.succeededFuture(new ApiResponse<>("OK" + invId));
    }
}
