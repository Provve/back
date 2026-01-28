package tech.provve.payment.service.application;

import tech.provve.payment.exception.PaymentGatewayNotAccessible;

/**
 * Сервис оплаты премиум-статуса.
 */
public interface PaymentService {

    /**
     * Запросить платёж.
     *
     * @return факт успешной оплаты
     */
    boolean createInvoice(String login) throws PaymentGatewayNotAccessible;

}
