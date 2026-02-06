package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.RobokassaConfirmPaymentRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface PaymentsApi {
    Future<ApiResponse<String>> confirmPayment(RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest);
}
