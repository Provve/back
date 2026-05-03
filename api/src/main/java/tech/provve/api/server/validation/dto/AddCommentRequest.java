package tech.provve.api.server.validation.dto;

import jakarta.validation.constraints.NotBlank;

public record AddCommentRequest(@NotBlank String content) {

}
