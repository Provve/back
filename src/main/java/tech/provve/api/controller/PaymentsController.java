package tech.provve.api.controller;

import alekseyvideman.dop.Collection;
import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.accounts.Account;
import tech.provve.api.ApiResponse;
import tech.provve.api.generated.api.PaymentsApi;
import tech.provve.api.generated.dto.RobokassaConfirmPaymentRequest;
import tech.provve.constants.Entity;
import tech.provve.payment.gateway.robokassa.PaymentRequest;
import tech.provve.payment.Payment;
import tech.provve.util.Storage;

public class PaymentsController implements PaymentsApi {

    @Override
    public Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest) {
        var accountLogin = robokassaConfirmPaymentRequest.getShp()
                                                         .get(PaymentRequest.ACCOUNT_FIELD);
        var signature = robokassaConfirmPaymentRequest.getSignatureValue();

        boolean confirmed = Payment.confirmPayment(accountLogin, signature);
        if (!confirmed) {
            return Future.failedFuture(new HttpException(400));
        }

        Storage.findAccountByLogin(accountLogin)
               .ifPresent(account -> Account.upgrade(Collection.get(account, Entity.Account.LOGIN)));

        int invId = robokassaConfirmPaymentRequest.getInvId();
        return Future.succeededFuture(new ApiResponse<>("OK" + invId));
    }
}
