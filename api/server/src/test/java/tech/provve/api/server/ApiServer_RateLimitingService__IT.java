package tech.provve.api.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.caffeine.Bucket4jCaffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.net.SocketAddress;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import tech.provve.api.server.controller.AccountsController;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.AuthenticateUser200Response;
import tech.provve.api.server.generated.dto.AuthenticateUserRequest;
import tech.provve.api.server.service.RateLimitingService;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

import static io.github.bucket4j.distributed.ExpirationAfterWriteStrategy.fixedTimeToLive;
import static io.vertx.core.net.SocketAddress.domainSocketAddress;
import static java.time.Duration.ofMinutes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static tech.provve.api.server.ApiServer.PORT;

@InjectTest
@ExtendWith(VertxExtension.class)
class ApiServer_RateLimitingService__IT {

    ObjectMapper objectMapper = new ObjectMapper();

    Supplier<BucketConfiguration> conf = () -> BucketConfiguration.builder()
                                                                  .addLimit(limit -> limit.capacity(1)
                                                                                          .refillIntervally(1, ofMinutes(1)))
                                                                  .build();

    static CaffeineProxyManager<SocketAddress> manager = Bucket4jCaffeine.<SocketAddress>builderFor(Caffeine.newBuilder()
                                                                                                            .maximumSize(1))
                                                                         .expirationAfterWrite(fixedTimeToLive(ofMinutes(5)))
                                                                         .build();

    @Mock
    ScheduledExecutorService scheduledExecutorService;

    @Mock
    AccountsController accountsController;

    BeanScopeBuilder beanScopeBuilder;

    @Setup
    void eachAvaje(BeanScopeBuilder b) {
        this.beanScopeBuilder = b.bean(ScheduledExecutorService.class, scheduledExecutorService);
    }

    @RepeatedTest(2)
    void apiAuth_manyRequests_sameKey_limitExceeded(Vertx vertx, VertxTestContext context, RepetitionInfo repetitionInfo) {
        // arrange
        var keys = List.of(
                domainSocketAddress("1"),
                domainSocketAddress("2")
        );

        when(accountsController.authenticateUser(any())).thenReturn(Future.succeededFuture(new ApiResponse<>(new AuthenticateUser200Response(""))));
        var rateLimitingService = new RateLimitingService(conf, manager) {
            @Override
            public boolean pass(SocketAddress key) {
                return super.pass(keys.getFirst());
            }
        };
        beanScopeBuilder.bean(RateLimitingService.class, rateLimitingService);

        var body = new AuthenticateUserRequest("w", "1");
        var apiServer = new ApiServer(beanScopeBuilder);
        var expectedCodes = List.of(200, 429);
        var client = vertx.createHttpClient();

        // act
        var result = vertx.deployVerticle(apiServer)
                          .compose(_ -> client.request(HttpMethod.POST, PORT, "localhost", "/api/v1/auth")
                                              .compose(req -> {
                                                  try {
                                                      var bodyJson = objectMapper.writeValueAsString(body);

                                                      return req.putHeader("Content-Type", "application/json")
                                                                .send(Buffer.buffer(bodyJson));
                                                  } catch (JsonProcessingException e) {
                                                      throw new RuntimeException(e);
                                                  }
                                              }));
        // assert
        result.onSuccess(response -> context.verify(() -> {
            int code = response.statusCode();
            var expected = expectedCodes.get(repetitionInfo.getCurrentRepetition() - 1);
            assertThat(code).isEqualTo(expected);
            context.completeNow();
        }));
    }

    @RepeatedTest(2)
    void apiAuth_manyRequests_differentKeys_ok(Vertx vertx, VertxTestContext context, RepetitionInfo repetitionInfo) {
        // arrange
        List<SocketAddress> keys = List.of(
                domainSocketAddress("3"),
                domainSocketAddress("4")
        );

        when(accountsController.authenticateUser(any())).thenReturn(Future.succeededFuture(new ApiResponse<>(new AuthenticateUser200Response(""))));
        var rateLimitingService = new RateLimitingService(conf, manager) {
            @Override
            public boolean pass(SocketAddress key) {
                return super.pass(keys.get(repetitionInfo.getCurrentRepetition() - 1));
            }
        };
        beanScopeBuilder.bean(RateLimitingService.class, rateLimitingService);

        var body = new AuthenticateUserRequest("w", "1");
        var apiServer = new ApiServer(beanScopeBuilder);
        var client = vertx.createHttpClient();

        // act
        var result = vertx.deployVerticle(apiServer)
                          .compose(_ -> client.request(HttpMethod.POST, PORT, "localhost", "/api/v1/auth")
                                              .compose(req -> {
                                                  try {
                                                      var bodyJson = objectMapper.writeValueAsString(body);

                                                      return req.putHeader("Content-Type", "application/json")
                                                                .send(Buffer.buffer(bodyJson));
                                                  } catch (JsonProcessingException e) {
                                                      throw new RuntimeException(e);
                                                  }
                                              }));
        // assert
        result.onSuccess(response -> context.verify(() -> {
            int code = response.statusCode();
            assertThat(code).isEqualTo(200);
            context.completeNow();
        }));
    }

}