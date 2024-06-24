package com.sparta.delivery_app.domain.review.dto.response;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ManagerReviewAddResponseDto {

    private String content;

    public static ManagerReviewAddResponseDto of(ManagerReviews managerReviews) {
        return ManagerReviewAddResponseDto.builder()
                .content(managerReviews.getContent())
                .build();
    }
}
