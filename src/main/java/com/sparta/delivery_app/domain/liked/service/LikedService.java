package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.SelfLikedException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.common.Page.PageConstants;
import com.sparta.delivery_app.domain.common.Page.PageUtil;
import com.sparta.delivery_app.domain.liked.adapter.LikedAdapter;
import com.sparta.delivery_app.domain.liked.dto.LikedStorePageResponseDto;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.liked.entity.ReviewLiked;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.openApi.dto.ReviewPageResponseDto;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import com.sparta.delivery_app.domain.review.repository.UserReviewsSearchCond;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.domain.store.repository.StoreSearchCond;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.domain.common.Page.PageConstants.PAGE_SIZE_FIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedAdapter likedAdapter;
    private final UserAdapter userAdapter;
    private final StoreAdapter storeAdapter;
    private final UserReviewsAdapter userReviewsAdapter;
    private final StoreRepository storeRepository;
    private final UserReviewsRepository userReviewsRepository;

    /**
     * 매장 좋아요 등록
     */
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

    /**
     * 좋아요한 매장 전체 조회
     */
    public LikedStorePageResponseDto getLikedStores(AuthenticationUser user, Integer pageNum, Boolean isDesc) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        StoreSearchCond cond = StoreSearchCond.builder()
                .likedUserId(findUser.getId())
                .build();
        Pageable pageable = PageUtil.createPageable(pageNum, PageConstants.PAGE_SIZE_FIVE, isDesc);

        Page<Store> storePage = storeRepository.searchLikedStore(cond, pageable);
        String totalLikedStore = PageUtil.validateAndSummarizePage(pageNum, storePage);

        return LikedStorePageResponseDto.of(pageNum, totalLikedStore, storePage);
    }

    /**
     * 리뷰 좋아요 등록
     */
    @Transactional
    public Boolean addReviewLiked(AuthenticationUser user, final Long reviewId) {
        UserReviews userReviews = userReviewsAdapter.checkValidReviewByIdAndReviewStatus(reviewId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        // 자기 자신의 리뷰인 경우 예외 처리
        if (userReviews.getUser().equals(findUser)) {
            throw new SelfLikedException(LikedErrorCode.SELF_LIKED_REGISTERED_ERROR);
        }

        Optional<ReviewLiked> findReviewLiked =  likedAdapter.getReviewLiked(userReviews.getId(), findUser.getId());
        ReviewLiked reviewLiked = (ReviewLiked) toggleLiked(findReviewLiked, findUser, ReviewLiked.class);
        reviewLiked.updateUserReviews(userReviews);

        likedAdapter.saveLiked(reviewLiked);
        return reviewLiked.getIsLiked();
    }

    /**
     * 좋아요한 리뷰 전체 조회
     */
    public ReviewPageResponseDto getLikedUserReviews(AuthenticationUser user, Integer pageNum, Boolean isDesc) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        UserReviewsSearchCond cond = UserReviewsSearchCond.builder()
                .likedUserId(findUser.getId())
                .build();
        Pageable pageable = PageUtil.createPageable(pageNum, PAGE_SIZE_FIVE, isDesc);

        Page<UserReviews> reviewPage = userReviewsRepository.searchLikedUserReviews(cond, pageable);
        String totalLikedReview = PageUtil.validateAndSummarizePage(pageNum, reviewPage);

        return ReviewPageResponseDto.of(pageNum, totalLikedReview, reviewPage);
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
