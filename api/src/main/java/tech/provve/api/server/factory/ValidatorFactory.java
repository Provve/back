package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Factory
public class ValidatorFactory {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory()
                         .getValidator();
    }

}
