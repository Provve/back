package tech.provve.payment.gateway.robokassa;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeException;
import dev.failsafe.RetryPolicy;
import io.avaje.config.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import tech.provve.payment.exception.PaymentGatewayNotAccessible;
import tech.provve.util.Jackson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;


public class ApiClient {

    private static final String MERCHANT_LOGIN = Config.get("robokassa.merchant-login");
    private static final Integer PREMIUM_PRICE = Config.getInt("premium-price");
    private static final byte[] JWT_SECRET = Config.get("robokassa.jwt-secret")
                                                   .getBytes(StandardCharsets.UTF_8);
    private static final String GET_PAYMENT_LINK_URL = "https://services.robokassa.ru/InvoiceServiceWebApi/api/CreateInvoice";

    private static final HttpRequest.Builder GET_PAYMENT_LINK_BUILDER = HttpRequest.newBuilder()
                                                                                   .uri(URI.create(GET_PAYMENT_LINK_URL));
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final RetryPolicy<String> RETRY_POLICY = RetryPolicy.<String>builder()
                                                                       .withDelay(Duration.ofSeconds(5))
                                                                       .withMaxRetries(3)
                                                                       .build();


    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @return ссылка для оплаты
     */
    @SneakyThrows
    public static String getPaymentLinkForPremium(String jsonRequestBody) {
        try {
            var request = GET_PAYMENT_LINK_BUILDER
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .build();
            return Failsafe.with(RETRY_POLICY)
                           .get(() -> HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString())
                                                 .body());
        } catch (FailsafeException e) {
            throw new PaymentGatewayNotAccessible(e);
        }
    }

    /**
     * Построить JWS для API Robokassa
     *
     * @param json тело запроса в json
     */
    public static String jws(String json) {
        return Jwts.builder()
                   .content(json)
                   .signWith(Keys.hmacShaKeyFor(JWT_SECRET))
                   .compact();
    }

    /**
     * @param accountLogin кто покупает
     * @return JSON
     */
    @SneakyThrows
    public static String jsonRequestBody(String accountLogin) {
        return Jackson.json.writeValueAsString(new PaymentRequest(
                MERCHANT_LOGIN,
                PREMIUM_PRICE,
                InvoiceType.OneTime,
                Map.of(PaymentRequest.ACCOUNT_FIELD, accountLogin)
        ));
    }

}
