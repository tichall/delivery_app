package com.sparta.delivery_app.domain.review.dto.response;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ManagerReviewResponseDto {

    private String content;

    public static ManagerReviewResponseDto of(ManagerReviews managerReviews) {
        return ManagerReviewResponseDto.builder()
                .content(managerReviews.getContent())
                .build();
    }
}
