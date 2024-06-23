package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerReviewsRepository extends JpaRepository<ManagerReviews, Long> {
}
