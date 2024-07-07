package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.liked.entity.ReviewLiked;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.ReviewLikedRepository;
import com.sparta.delivery_app.domain.liked.repository.StoreLikedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikedAdapter {

    private final StoreLikedRepository storeLikedRepository;
    private final ReviewLikedRepository reviewLikedRepository;

    public void saveLiked(Liked liked) {
        if (liked.getClass().equals(StoreLiked.class)) {
            storeLikedRepository.save((StoreLiked) liked);
        } else if (liked.getClass().equals(ReviewLiked.class)) {
            reviewLikedRepository.save((ReviewLiked) liked);
        }
    }

    public Optional<StoreLiked> getStoreLiked(Long storeId, Long userId) {
        return storeLikedRepository.findByStoreIdAndUserId(storeId, userId);
    }

    public Optional<ReviewLiked> getReviewLiked(Long reviewId, Long userId) {
        return reviewLikedRepository.findByUserReviewsIdAndUserId(reviewId, userId);
    }
}
