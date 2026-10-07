package tech.provve.api.generated.api;

import tech.provve.api.generated.dto.RobokassaConfirmPaymentRequest;

import tech.provve.api.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface PaymentsApi {
    Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest);
}
