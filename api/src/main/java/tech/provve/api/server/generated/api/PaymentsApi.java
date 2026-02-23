package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.RobokassaConfirmPaymentRequest;

public interface PaymentsApi {
    Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest);
}
