package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReviewsRepositoryCustom {
    Page<UserReviews> searchLikedUserReviews(UserReviewsSearchCond cond, Pageable pageable);

    Long countTotalLikedUserReviews(Long userId);
}
