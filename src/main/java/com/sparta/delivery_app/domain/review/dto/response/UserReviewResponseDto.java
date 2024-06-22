package com.sparta.delivery_app.domain.review.dto.response;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserReviewResponseDto {

    private String storeName;
    private String userName;
    private String content;
    private String reviewImagePath;
    private int rating;

    public static UserReviewResponseDto of(UserReviews userReviews) {
        return UserReviewResponseDto.builder()
                .storeName(userReviews.getOrder().getStore().getStoreName())
                .userName(userReviews.getUser().getNickName())
                .content(userReviews.getContent())
                .reviewImagePath(userReviews.getReviewImagePath())
                .rating(userReviews.getRating())
                .build();
    }

}
