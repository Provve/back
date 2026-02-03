package tech.provve.payment.service.application;

import io.avaje.inject.External;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.exception.AccountAlreadyUpgraded;
import tech.provve.accounts.exception.AccountNotFound;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.payment.domain.value.Invoice;
import tech.provve.payment.gateway.robokassa.ApiClient;
import tech.provve.payment.repository.RobokassaInvoiceRepository;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PaymentServiceImpl implements PaymentService {

    private final ApiClient apiClient;

    private final RobokassaInvoiceRepository invoiceRepository;

    @External
    private final AccountRepository accountRepository;

    @Override
    public String createInvoice(String accountLogin) {
        accountRepository.findByLogin(accountLogin)
                         .ifPresentOrElse(
                                 a -> {
                                     if (a.isPremium()) {
                                         throw new AccountAlreadyUpgraded("Account with login '%s' already upgraded".formatted(
                                                 accountLogin));
                                     }
                                 }, () -> {
                                     throw new AccountNotFound("Account with login '%s' not found".formatted(
                                             accountLogin));
                                 }
                         );

        var requestJson = apiClient.jsonRequestBody(accountLogin);
        var jws = apiClient.jws(requestJson);

        invoiceRepository.save(new Invoice(accountLogin, jws));

        return apiClient.getPaymentLinkForPremium(jws);
    }

    @Override
    public boolean confirmPayment(String accountLogin, String signature) {
        var invoice = invoiceRepository.findBy(accountLogin);
        return invoice.filter(value -> signature.equals(value.signature()))
                      .isPresent();
    }
}
