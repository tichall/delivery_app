package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.liked.entity.ReviewLiked;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.ReviewLikedRepository;
import com.sparta.delivery_app.domain.liked.repository.StoreLikedRepository;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
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

    public void deleteLiked(StoreLiked storeLiked) {
        storeLikedRepository.delete(storeLiked);
    }

    public StoreLiked queryLikedByStoreId(Long storeId) {
        return storeLikedRepository.findByStoreId(storeId).orElseThrow(() ->
                new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR));
    }

    public boolean existsByStoreAndUser(Store store, User user) {
        return storeLikedRepository.existsByStoreAndUser(store, user);
    }

    public Optional<StoreLiked> getStoreLiked(Long storeId, Long userId) {
        return storeLikedRepository.findByStoreIdAndUserId(storeId, userId);
    }

    public Optional<ReviewLiked> getReviewLiked(Long reviewId, Long userId) {
        return reviewLikedRepository.findByUserReviewsIdAndUserId(reviewId, userId);
    }
}
