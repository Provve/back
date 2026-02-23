package tech.provve.skill.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Factory
public class DeadlineFactory {

    @Bean
    public Supplier<LocalDateTime> deadlineSupplier() {
        return () -> LocalDateTime.now()
                                  .plusMonths(1);
    }

}
