package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CastVote(@NotBlank String name,
                       @NotBlank String authToken,
                       @NotNull Boolean reaction
) {

}
