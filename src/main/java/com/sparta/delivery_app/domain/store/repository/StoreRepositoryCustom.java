package com.sparta.delivery_app.domain.store.repository;

import com.sparta.delivery_app.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {
    Page<Store> searchLikedStore(StoreSearchCond cond, Pageable pageable);

    Long countTotalLikedStore(Long userId);

    List<Store> findTotalLikedTopTenStore();

//    StoreDetailsResponseDto findStoreDetails(Long storeId);
}
