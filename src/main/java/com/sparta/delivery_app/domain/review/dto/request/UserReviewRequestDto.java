package com.sparta.delivery_app.domain.review.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserReviewRequestDto {

    @NotNull(message = "내용을 입력해 주세요")
    @Size(min = 10, max = 255)
    private String content;

    private String reviewImagePath;

    @NotNull(message = "별점을 등록해 주세요.")
    @Min(1) @Max(5)
    private int rating;
}
