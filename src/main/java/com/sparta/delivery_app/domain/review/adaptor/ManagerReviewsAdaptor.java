package com.sparta.delivery_app.domain.review.adaptor;

import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.repository.ManagerReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerReviewsAdaptor {

    private final ManagerReviewsRepository managerReviewsRepository;

    public void saveReview(ManagerReviews managerReviews) {
        managerReviewsRepository.save(managerReviews);
    }
}
