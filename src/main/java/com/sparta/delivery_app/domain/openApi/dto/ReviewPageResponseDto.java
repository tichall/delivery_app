package com.sparta.delivery_app.domain.openApi.dto;


import com.sparta.delivery_app.domain.review.dto.response.UserReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class ReviewPageResponseDto {

    private Integer currentPage;
    private String totalReview;
    private List<UserReviewAddResponseDto> reviewList;

    public static ReviewPageResponseDto of(Integer currentPage, String totalReview, Page<UserReviews> reviewPage) {
        return ReviewPageResponseDto.builder()
                .currentPage(currentPage)
                .totalReview(totalReview)
                .reviewList(reviewPage.getContent().stream()
                        .map(UserReviewAddResponseDto::of).toList())
                .build();
    }
}