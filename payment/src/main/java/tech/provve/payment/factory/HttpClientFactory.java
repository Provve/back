package tech.provve.payment.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.net.URI;
import java.net.http.HttpRequest;

@Factory
public class HttpClientFactory {

    public static final String GET_PAYMENT_LINK_URL = "https://services.robokassa.ru/InvoiceServiceWebApi/api/CreateInvoice";

    @Bean
    public HttpRequest.Builder getPaymentLinkBuilder() {
        return HttpRequest.newBuilder()
                          .uri(URI.create(GET_PAYMENT_LINK_URL));
    }

}
