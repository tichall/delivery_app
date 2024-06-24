package com.sparta.delivery_app.domain.openApi.adapter;

import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenApiAdapter {

    private final StoreRepository storeRepository;
    private final StoreAdaptor storeAdaptor;
    private final UserReviewsRepository userReviewsRepository;

    /**
     * ENABLE 상태인 매장만 조회
     * @return
     */
    public Page<Store> queryStores(Pageable pageable) {
        return storeRepository.findAllByStatus(pageable, StoreStatus.ENABLE);
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @return
     */
    public StoreDetailsResponseDto queryMenusByStoreId(Long storeId) {

        Store store = storeAdaptor.queryStoreById(storeId);
        StoreStatus.checkStoreStatus(store);

        return new StoreDetailsResponseDto(store);
    }

    /**
     * ENABLE 상태인 사용자 리뷰만 조회
     * @param pageable
     * @return
     */
    public Page<UserReviews> queryReviews(Pageable pageable) {
        return userReviewsRepository.findAllByReviewStatus(pageable, ReviewStatus.ENABLE);
    }
}
