package tech.provve.api.server.factory;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.caffeine.Bucket4jCaffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.vertx.core.Handler;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Named;
import tech.provve.api.server.service.RateLimitingService;

import java.util.function.Supplier;

import static io.github.bucket4j.distributed.ExpirationAfterWriteStrategy.fixedTimeToLive;
import static io.netty.handler.codec.http.HttpResponseStatus.TOO_MANY_REQUESTS;
import static java.time.Duration.ofMinutes;

@Factory
public class RateLimit {

    public static final String RATE_LIMITER = "RATE_LIMITER";

    @Bean
    public Supplier<BucketConfiguration> bucketConfigurationSupplier() {
        return () -> BucketConfiguration.builder()
                                        .addLimit(limit -> limit.capacity(1)
                                                                .refillIntervally(1, ofMinutes(1)))
                                        .build();
    }

    @Bean
    public CaffeineProxyManager<SocketAddress> caffeineProxyManager() {
        return Bucket4jCaffeine.<SocketAddress>builderFor(Caffeine.newBuilder()
                                                                  .maximumSize(1))
                               .expirationAfterWrite(fixedTimeToLive(ofMinutes(5)))
                               .build();
    }

    @Bean
    @Named(RATE_LIMITER)
    public Handler<RoutingContext> routingContextHandler(RateLimitingService rateLimitingService) {
        return context -> {
            if (!"/api/v1/auth".equals(context.request()
                                              .path())) {
                context.next();
                return;
            }
            var senderAddress = context.request()
                                       .remoteAddress();
            if (rateLimitingService.pass(senderAddress)) {
                context.next();
                return;
            }

            context.response()
                   .setStatusCode(TOO_MANY_REQUESTS.code())
                   .end();
        };
    }

}
