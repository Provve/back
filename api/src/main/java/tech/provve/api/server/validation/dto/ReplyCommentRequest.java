package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ReplyCommentRequest(@NotNull @PositiveOrZero Integer targetId, @NotBlank String content) {

}
