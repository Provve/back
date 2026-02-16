package tech.provve.api.server.factory;

import dev.failsafe.RetryPolicy;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

@Factory
public class HttpClientFactory {

    public static final String GET_PAYMENT_LINK_URL = "https://services.robokassa.ru/InvoiceServiceWebApi/api/CreateInvoice";

    @Bean
    public HttpRequest.Builder getPaymentLinkBuilder() {
        return HttpRequest.newBuilder()
                          .uri(URI.create(GET_PAYMENT_LINK_URL));
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public RetryPolicy<String> retryPolicy() {
        return RetryPolicy.<String>builder()
                          .withDelay(Duration.ofSeconds(5))
                          .withMaxRetries(3)
                          .build();
    }

}
