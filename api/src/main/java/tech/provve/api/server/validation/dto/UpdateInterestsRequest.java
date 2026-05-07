package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateInterestsRequest(@NotEmpty List<String> interests) {

}
