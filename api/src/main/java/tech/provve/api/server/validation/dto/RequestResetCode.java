package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestResetCode(@NotBlank @Email String email) {

}
