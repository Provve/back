package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static tech.provve.api.server.validation.Constraints.LOGIN_MAX_LENGTH;

public record AuthenticateUserRequest(
        @NotBlank
        @Size(max = LOGIN_MAX_LENGTH)
        String login,
        @NotBlank String password
) {

}