package tech.provve.api.server.validation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CollectionRequest(@Valid Filter filter) {

    public record Filter(@NotBlank String field, @Valid FilterPredicate predicate) {

    }

    public record FilterPredicate(@NotNull tech.provve.api.server.generated.dto.FilterPredicate.OperatorEnum operator, @NotBlank String value) {

    }

}
