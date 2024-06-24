package com.sparta.delivery_app.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserReviewModifyRequestDto(
        @NotNull(message = "내용을 입력해 주세요")
        @Size(min = 10, max = 255)
        String content,

        String reviewImagePath,

        @NotNull(message = "별점을 등록해 주세요.")
        @Min(1) @Max(5)
        int rating
) {
}
