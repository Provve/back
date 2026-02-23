package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;

public record SkillDelVote(@NotBlank String name,
                           @NotBlank String arguments,
                           @NotBlank String authToken
) {

}
