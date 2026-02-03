package tech.provve.payment.service.application;

import tech.provve.accounts.exception.AccountAlreadyUpgraded;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.payment.exception.PaymentGatewayNotAccessible;

/**
 * Прикладной сервис оплаты премиум-статуса.
 */
public interface PaymentService {

    /**
     * Запросить платёж.
     *
     * @return ссылка на оплату
     */
    String createInvoice(String accountLogin) throws PaymentGatewayNotAccessible, AccountNotFound, AccountAlreadyUpgraded;

    /**
     * Подтвердить прошедшую оплату
     *
     * @return подтверждена или нет
     */
    boolean confirmPayment(String accountLogin, String signature);

}
