package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReviewsRepository extends JpaRepository<UserReviews, Long> {
//    Page<UserReviews> findAllUserReviewsByOrderId(Long orderId, Pageable pageable);
    Optional<Long> findManagerReviewIdById(Long reviewId);

    UserReviews findUserReviewsByOrderId(Long orderId);
}
