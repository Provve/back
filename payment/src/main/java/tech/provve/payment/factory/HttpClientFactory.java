package tech.provve.payment.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.net.URI;
import java.net.http.HttpRequest;

@Factory
public class HttpClientFactory {

    @Bean
    public HttpRequest.Builder getPaymentLinkBuilder() {
        return HttpRequest.newBuilder()
                          .uri(URI.create("https://services.robokassa.ru/InvoiceServiceWebApi/api/CreateInvoice"));
    }

}
