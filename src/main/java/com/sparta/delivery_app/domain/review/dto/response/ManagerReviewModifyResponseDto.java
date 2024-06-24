package com.sparta.delivery_app.domain.review.dto.response;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ManagerReviewModifyResponseDto {

    private String content;

    public static ManagerReviewModifyResponseDto of(ManagerReviews managerReviews) {
        return ManagerReviewModifyResponseDto.builder()
                .content(managerReviews.getContent())
                .build();
    }
}
