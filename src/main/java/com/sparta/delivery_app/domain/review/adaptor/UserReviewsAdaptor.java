package com.sparta.delivery_app.domain.review.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.ReviewStatusException;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReviewsAdaptor {

    private final UserReviewsRepository userReviewsRepository;

    /**
     * 리뷰 등록
     */
    public void saveReview(UserReviews userReviews) {
        userReviewsRepository.save(userReviews);
    }

    /**
     * 리뷰 Id 검증
     */
    public UserReviews findById(Long reviewId) {
        return userReviewsRepository.findById(reviewId).orElseThrow(() ->
                new ReviewStatusException(ReviewErrorCode.INVALID_REVIEW));
    }


    /**
     * 메뉴 id, 상태 검증
     */
    public UserReviews checkValidReviewByIdAndReviewStatus(Long reviewId) {
        UserReviews userReviews = findById(reviewId);

        if(userReviews.getReviewStatus().equals(ReviewStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }

        return userReviews;
    }
}
