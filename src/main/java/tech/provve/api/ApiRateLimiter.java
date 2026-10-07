package tech.provve.api;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.caffeine.Bucket4jCaffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.distributed.proxy.RecoveryStrategy;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;


import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static io.github.bucket4j.distributed.ExpirationAfterWriteStrategy.fixedTimeToLive;
import static io.netty.handler.codec.http.HttpResponseStatus.TOO_MANY_REQUESTS;
import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;


public class ApiRateLimiter {

    private static final Supplier<BucketConfiguration> BUCKET_CONFIGURATION_SUPPLIER = () -> BucketConfiguration.builder()
                                                                                                                .addLimit(limit -> limit.capacity(10)
                                                                                                                                        .refillIntervally(1, ofSeconds(1)))
                                                                                                                .build();

    private static final CaffeineProxyManager<SocketAddress> MANAGER = Bucket4jCaffeine.<SocketAddress>builderFor(Caffeine.newBuilder()
                                                                                                                          .maximumSize(1))
                                                                                       .expirationAfterWrite(fixedTimeToLive(ofMinutes(5)))
                                                                                       .build();

    /**
     * Запросить пропуск, уменьшив счетчик на 1.
     *
     * @return попущен ли
     */
    public static boolean pass(SocketAddress key) {
        return MANAGER.builder()
                      .withRecoveryStrategy(RecoveryStrategy.RECONSTRUCT)
                      .build(key, BUCKET_CONFIGURATION_SUPPLIER)
                      .tryConsume(1);
    }

    public static void handle(RoutingContext context) {
        if (!"/api/v1/auth".equals(context.request()
                                          .path())) {
            context.next();
            return;
        }
        var senderAddress = context.request()
                                   .remoteAddress();
        if (pass(senderAddress)) {
            context.next();
            return;
        }

        context.response()
               .setStatusCode(TOO_MANY_REQUESTS.code())
               .end();
    }

}
