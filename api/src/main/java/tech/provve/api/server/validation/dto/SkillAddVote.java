package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

import static tech.provve.api.server.validation.Constraints.VOTE_NAME_MAX_LENGTH;
import static tech.provve.api.server.validation.Constraints.VOTE_TAG_MAX_LENGTH;

public record SkillAddVote(@NotBlank @Size(max = VOTE_NAME_MAX_LENGTH) String name,
                           @NotBlank String arguments,
                           @NotEmpty List<@NotBlank @Size(max = VOTE_TAG_MAX_LENGTH) String> tags,
                           @NotBlank String authToken
) {

}
