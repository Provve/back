package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePersonalDataConsentRequest(
        @NotNull Boolean consentPersonalData,
        @NotBlank String authToken
) {

}
