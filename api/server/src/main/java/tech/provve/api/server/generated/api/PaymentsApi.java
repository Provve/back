package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.RobokassaConfirmPaymentRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;

public interface PaymentsApi {

    Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest);
}
