package tech.provve.payment.exception;

public class PaymentGatewayNotAccessible extends RuntimeException {

    public PaymentGatewayNotAccessible(Throwable cause) {
        super(cause);
    }
}
