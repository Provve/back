package tech.provve.payment.gateway.robokassa;

import dev.failsafe.RetryPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.provve.payment.exception.PaymentGatewayNotAccessible;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiClientTest {

    HttpRequest.Builder stubRequestBuilder = HttpRequest.newBuilder()
                                                        .uri(URI.create("https://services.robokassa.ru/InvoiceServiceWebApi/api/CreateInvoice"));

    @Mock
    HttpClient httpClient;

    @Mock
    HttpResponse<String> httpResponse;

    @Test
    void getPaymentLinkForPremium_robokassaAvailable_returnedValue() throws IOException, InterruptedException {
        var apiClient = new ApiClient(
                stubRequestBuilder, httpClient, RetryPolicy.<String>builder()
                                                           .withDelay(Duration.ofSeconds(1))
                                                           .build()
        );
        // arrange
        when(httpResponse.body()).thenReturn("a");
        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(httpResponse);

        // act
        var result = apiClient.getPaymentLinkForPremium("b");

        // assert
        assertThat(result).isEqualTo("a");
    }

    @Test
    void getPaymentLinkForPremium_robokassaUnavailable_exception() throws IOException, InterruptedException {
        var apiClient = new ApiClient(
                stubRequestBuilder, httpClient, RetryPolicy.<String>builder()
                                                           .withDelay(Duration.ofSeconds(1))
                                                           .build()
        );
        // arrange
        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenThrow(ConnectException.class);

        // act

        // act assert
        assertThatThrownBy(() -> apiClient.getPaymentLinkForPremium("b")).isExactlyInstanceOf(PaymentGatewayNotAccessible.class);
    }


}