package tech.provve.payment.exception;

public class PaymentGatewayNotAccessible extends RuntimeException {

    public PaymentGatewayNotAccessible(String message) {
        super(message);
    }
}
