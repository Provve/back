package tech.provve.payment;

import alekseyvideman.dop.Collection;
import tech.provve.accounts.exception.AccountAlreadyUpgraded;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.constants.Entity;
import tech.provve.payment.gateway.robokassa.ApiClient;
import tech.provve.util.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Прикладной сервис оплаты премиум-статуса.
 */
public class Payment {

    public static String createInvoice(String accountLogin) {
        Storage.findAccountByLogin(accountLogin)
               .ifPresentOrElse(
                       a -> {
                           if (Boolean.TRUE.equals(Collection.get(a, Entity.Account.IS_PREMIUM))) {
                               throw new AccountAlreadyUpgraded("Account with login '%s' already upgraded".formatted(
                                       accountLogin));
                           }
                       }, () -> {
                           throw new AccountNotFound("Account with login '%s' not found".formatted(
                                   accountLogin));
                       }
               );

        var requestJson = ApiClient.jsonRequestBody(accountLogin);
        var jws = ApiClient.jws(requestJson);
        var paymentLink = ApiClient.getPaymentLinkForPremium(jws);

        Map<String, Object> invoice = new HashMap<>();
        invoice.put(Entity.Invoice.ACCOUNT_LOGIN, accountLogin);
        invoice.put(Entity.Invoice.SIGNATURE, jws);
        Storage.saveInvoice(invoice);

        return paymentLink;
    }

    public static boolean confirmPayment(String accountLogin, String signature) {
        Optional<Map<String, Object>> invoice = Storage.findInvoiceBy(accountLogin);
        return invoice.filter(value -> signature.equals(Collection.get(value, Entity.Invoice.SIGNATURE)))
                      .isPresent();
    }
}
