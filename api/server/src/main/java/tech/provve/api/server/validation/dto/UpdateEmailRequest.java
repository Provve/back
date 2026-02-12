package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(@NotBlank @Email String email, @NotBlank String authToken) {

}