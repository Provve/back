package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(@NotBlank String resetToken,
                                    @NotBlank String newPassword
) {

}
