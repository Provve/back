package tech.provve.accounts.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import jakarta.inject.Singleton;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Factory
public class ExecutorFactory {

    @Bean
    @Singleton
    public ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(
                1,
                Thread.ofVirtual()
                      .factory()
        );
    }

}
