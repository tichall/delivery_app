package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerReviewsRepository extends JpaRepository<ManagerReviews, Long> {
}
