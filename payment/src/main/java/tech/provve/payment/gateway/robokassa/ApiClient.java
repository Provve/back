package tech.provve.payment.gateway.robokassa;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.avaje.config.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tech.provve.payment.exception.PaymentGatewayNotAccessible;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ApiClient {

    private static final String MERCHANT_LOGIN = Config.get("robokassa.merchant-login");

    private static final Integer PREMIUM_PRICE = Config.getInt("premium-price");

    private static final byte[] JWT_SECRET = Config.get("robokassa.jwt-secret")
                                                   .getBytes(StandardCharsets.UTF_8);

    private final HttpRequest.Builder getPaymentLinkBuilder;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @return ссылка для оплаты
     */
    @SneakyThrows
    public String getPaymentLinkForPremium(String jsonRequestBody) {
        try {
            var request = getPaymentLinkBuilder
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
                             .body();

        } catch (IOException e) {
            throw new PaymentGatewayNotAccessible(e);
        }
    }

    /**
     * Построить JWS для API Robokassa
     *
     * @param json тело запроса в json
     */
    public String jws(String json) {
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
    public String jsonRequestBody(String accountLogin) {
        return objectMapper.writeValueAsString(new PaymentRequest(
                MERCHANT_LOGIN,
                PREMIUM_PRICE,
                InvoiceType.OneTime,
                Map.of(PaymentRequest.ACCOUNT_FIELD, accountLogin)
        ));
    }

}
