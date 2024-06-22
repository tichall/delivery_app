package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewsRepository extends JpaRepository<UserReviews, Long> {
}
