package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.liked.adapter.LikedAdapter;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.liked.entity.ReviewLiked;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedAdapter likedAdapter;
    private final UserAdapter userAdapter;
    private final StoreAdapter storeAdapter;
    private final UserReviewsAdapter userReviewsAdapter;

    @Transactional
    public Boolean addStoreLiked(AuthenticationUser user, final Long storeId) {
        Store store = storeAdapter.queryStoreById(storeId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Optional<StoreLiked> findStoreLiked =  likedAdapter.getStoreLiked(store.getId(), findUser.getId());
        StoreLiked storeLiked = (StoreLiked) toggleLiked(findStoreLiked, findUser, StoreLiked.class);
        storeLiked.updateStore(store);

        likedAdapter.saveLiked(storeLiked);
        return storeLiked.getIsLiked();
    }

    @Transactional
    public Boolean addReviewLiked(AuthenticationUser user, final Long reviewId) {
        UserReviews userReviews = userReviewsAdapter.checkValidReviewByIdAndReviewStatus(reviewId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Optional<ReviewLiked> findReviewLiked =  likedAdapter.getReviewLiked(userReviews.getId(), findUser.getId());
        ReviewLiked reviewLiked = (ReviewLiked) toggleLiked(findReviewLiked, findUser, ReviewLiked.class);
        reviewLiked.updateUserReviews(userReviews);

        likedAdapter.saveLiked(reviewLiked);
        return reviewLiked.getIsLiked();
    }


    @Transactional
    public void deleteLiked(AuthenticationUser user, final Long storeId) {
        Store store = storeAdapter.queryStoreById(storeId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        if (!likedAdapter.existsByStoreAndUser(store, findUser)) {
            throw new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR);
        }

        StoreLiked findStoreLiked = likedAdapter.queryLikedByStoreId(storeId);
        likedAdapter.deleteLiked(findStoreLiked);
    }

    private <T extends Liked> Liked toggleLiked(Optional<T> liked, User user, Class<T> likedClass) {
        if (liked.isPresent()) {
            Liked findLiked = liked.get();
            findLiked.updateIsLiked();
            return findLiked;
        } else {
            if (likedClass.equals(StoreLiked.class)) {
                return StoreLiked.builder()
                        .user(user)
                        .build();
            } else {
                return ReviewLiked.builder()
                        .user(user)
                        .build();
            }
        }
    }
}
