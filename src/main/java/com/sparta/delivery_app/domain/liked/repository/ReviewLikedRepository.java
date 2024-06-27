package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.ReviewLiked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikedRepository extends JpaRepository<ReviewLiked, Long> {
    Optional<ReviewLiked> findByUserReviewsIdAndUserId(Long reviewId, Long userId);
}
