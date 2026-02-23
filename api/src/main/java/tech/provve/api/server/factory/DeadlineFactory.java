package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Supplier;

@Factory
public class DeadlineFactory {

    @Bean
    public Supplier<LocalDateTime> deadlineSupplier() {
        return () -> LocalDateTime.now(ZoneOffset.UTC)
                                  .plusMonths(1);
    }

}
