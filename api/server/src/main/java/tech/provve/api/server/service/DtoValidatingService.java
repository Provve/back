package tech.provve.api.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.dto.Error;

import java.util.List;
import java.util.Set;

@Singleton
public class DtoValidatingService {

    private final Validator validator;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public DtoValidatingService(Validator validator) {
        this.validator = validator;
    }

    @SneakyThrows
    public <T> void validate(T object) throws ValidationError {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            List<Error> errors = violations.stream()
                                           .map(violation -> {
                                               var about = violation.getMessage();
                                               var what = violation.getPropertyPath()
                                                                   .toString();
                                               return what + " " + about;
                                           })
                                           .map(Error::new)
                                           .toList();

            throw new ValidationError(objectMapper.writeValueAsString(errors));
        }
    }
}
