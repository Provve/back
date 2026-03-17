package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.net.URI;

public record CreateSessionRequest(@NotBlank String examName,
                                   @NotNull URI redirect,
                                   @NotBlank String authToken
) {

}
