package com.sparta.delivery_app.domain.review.dto.response;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserReviewAddResponseDto {

    private String storeName;
    private String userName;
    private int rating;
    private String content;
    private String reviewImagePath;
    private Integer totalReviewLiked;

    public static UserReviewAddResponseDto of(UserReviews userReviews) {
        return UserReviewAddResponseDto.builder()
                .storeName(userReviews.getOrder().getStore().getStoreName())
                .userName(userReviews.getUser().getNickName())
                .rating(userReviews.getRating())
                .content(userReviews.getContent())
                .reviewImagePath(userReviews.getReviewImagePath())
                .totalReviewLiked(userReviews.getReviewLikedList().size())
                .build();
    }

}
