package tech.provve.payment.service.application;

import jakarta.inject.Singleton;

@Singleton
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean createInvoice(String login) {
        throw new RuntimeException("impl me");
    }
}
