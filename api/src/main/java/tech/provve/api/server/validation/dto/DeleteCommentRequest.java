package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record DeleteCommentRequest(@NotNull @PositiveOrZero Integer id) {

}
