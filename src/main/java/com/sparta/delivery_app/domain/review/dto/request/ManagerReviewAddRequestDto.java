package com.sparta.delivery_app.domain.review.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ManagerReviewAddRequestDto(
        @NotNull(message = "내용을 입력해 주세요")
        @Size(min = 10, max = 255)
        String content
) {
}
