package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.provve.api.server.validation.validator.LegalEmail;

import static tech.provve.api.server.validation.Constraints.LOGIN_MAX_LENGTH;
import static tech.provve.api.server.validation.Constraints.USERNAME_MAX_LENGTH;

@LegalEmail
public record RegisterAccountRequest(
        @NotBlank
        @Size(max = LOGIN_MAX_LENGTH)
        String login,
        String email,
        @NotBlank String password,
        @NotNull Boolean consentPersonalData,
        @NotBlank
        @Size(max = USERNAME_MAX_LENGTH)
        String username
) {

}
