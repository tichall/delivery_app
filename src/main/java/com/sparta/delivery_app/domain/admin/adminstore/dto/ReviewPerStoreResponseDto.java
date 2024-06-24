package com.sparta.delivery_app.domain.admin.adminstore.dto;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.ManagerReviewsStatus;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewPerStoreResponseDto {

    //기본 속성
    private Long orderId;
//    private Long storeId;

    //userReview 속성
    private Long userReviewId;
    private int rating;
    private Long userId;
    private String userName;
    private UserStatus userStatus;
    private String reviewImagePath;
    private String userContent;
    private ReviewStatus reviewStatus;

    //ManagerReview 속성
    private Long managerId;
    private String managerReviewContent;
    private ManagerReviewsStatus managerReviewsStatus;

    public static ReviewPerStoreResponseDto of(UserReviews userReview) {

        ManagerReviews managerReview = userReview.getManagerReviews();

        return ReviewPerStoreResponseDto.builder()
                .orderId(userReview.getOrder().getId())
                .userReviewId(userReview.getId())
                .rating(userReview.getRating())
                .userId(userReview.getUser().getId())
                .userName(userReview.getUser().getName())
                .userStatus(userReview.getUser().getUserStatus())
                .reviewImagePath(userReview.getReviewImagePath())
                .userContent(userReview.getContent())
                .reviewStatus(userReview.getReviewStatus())

                .managerId(managerReview == null? null : managerReview.getId())
                .managerReviewContent(managerReview == null? null : managerReview.getContent())
                .managerReviewsStatus(managerReview == null? null : managerReview.getManagerReviewsStatus())
                .build();
    }
}
