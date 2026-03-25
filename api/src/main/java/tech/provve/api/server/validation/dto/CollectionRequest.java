package tech.provve.api.server.validation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CollectionRequest(@Valid Filter filter) {

    public record Filter(@NotEmpty List<@Valid Condition> conditions) {

    }

    public record Condition(@NotBlank String field, @NotBlank String value, @NotNull tech.provve.api.server.generated.dto.Condition.OperatorEnum operator) {

    }

}
