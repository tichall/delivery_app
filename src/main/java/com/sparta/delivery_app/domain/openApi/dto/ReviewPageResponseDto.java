package com.sparta.delivery_app.domain.openApi.dto;

import com.sparta.delivery_app.domain.review.dto.response.UserReviewResponseDto;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class ReviewPageResponseDto {

    private Integer currentPage;
    private Long totalReviews;
    private List<UserReviewResponseDto> reviewList;

    public static ReviewPageResponseDto of(Integer currentPage, Page<UserReviews> reviewPage) {
        return ReviewPageResponseDto.builder()
                .currentPage(currentPage)
                .totalReviews(reviewPage.getTotalElements())
                .reviewList(reviewPage.getContent().stream()
                        .filter(b -> b.getReviewStatus().equals(ReviewStatus.ENABLE))
                        .filter(a -> a.getUser().getUserRole().equals(UserRole.CONSUMER))
                        .map(UserReviewResponseDto::of).toList())
                .build();
    }
}