package tech.provve.api.generated.api;

import io.vertx.core.Future;
import tech.provve.api.ApiResponse;
import tech.provve.api.generated.dto.RobokassaConfirmPaymentRequest;

public interface PaymentsApi {
    Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest);
}
