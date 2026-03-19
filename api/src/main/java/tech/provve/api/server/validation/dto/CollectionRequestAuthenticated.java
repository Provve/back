package tech.provve.api.server.validation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CollectionRequestAuthenticated(@Valid CollectionRequest collectionRequest, @NotBlank String authToken) {

}
