package tech.provve.payment.service.application;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.payment.domain.value.Invoice;
import tech.provve.payment.gateway.robokassa.ApiClient;
import tech.provve.payment.repository.RobokassaInvoiceRepository;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PaymentServiceImpl implements PaymentService {

    private final ApiClient apiClient;
    private final RobokassaInvoiceRepository invoiceRepository;

    @Override
    public String createInvoice(String accountLogin) {
        var requestJson = apiClient.jsonRequestBody(accountLogin);
        var jws = apiClient.jws(requestJson);
        var paymentLink = apiClient.getPaymentLinkForPremium(jws);

        invoiceRepository.save(new Invoice(accountLogin, jws));

        return paymentLink;
    }

    @Override
    public boolean confirmPayment(String accountLogin, String signature) {
        var invoice = invoiceRepository.findBy(accountLogin);
        return invoice.filter(i -> signature.equals(i.signature()))
                      .isPresent();
    }
}
