package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static tech.provve.api.server.validation.Constraints.VOTE_NAME_MAX_LENGTH;

public record SkillAddVote(@NotBlank @Size(max = VOTE_NAME_MAX_LENGTH) String name,
                           @NotBlank String arguments,
                           @NotBlank String authToken
) {

}
