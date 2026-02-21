package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateAvatarRequest(@NotBlank String authToken) {

}
