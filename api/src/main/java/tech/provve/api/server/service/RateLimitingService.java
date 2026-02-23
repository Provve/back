package tech.provve.api.server.service;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.distributed.proxy.RecoveryStrategy;
import io.vertx.core.net.SocketAddress;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class RateLimitingService {

    private final Supplier<BucketConfiguration> bucketConfigurationSupplier;

    private final CaffeineProxyManager<SocketAddress> manager;

    /**
     * Запросить пропуск, уменьшив счетчик на 1.
     *
     * @return попущен ли
     */
    public boolean pass(SocketAddress key) {
        return manager.builder()
                      .withRecoveryStrategy(RecoveryStrategy.RECONSTRUCT)
                      .build(key, bucketConfigurationSupplier)
                      .tryConsume(1);
    }

}
