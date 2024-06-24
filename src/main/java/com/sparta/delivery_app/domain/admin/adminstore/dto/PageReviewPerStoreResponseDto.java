package com.sparta.delivery_app.domain.admin.adminstore.dto;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageReviewPerStoreResponseDto {

    private final Integer pageNum;
    private final Long storeId;
    private final String storeName;
    private final List<ReviewPerStoreResponseDto> reviewPerStoreList;

    public static PageReviewPerStoreResponseDto of(List<ReviewPerStoreResponseDto> reviewList, Integer pageNum, Store store) {

        return PageReviewPerStoreResponseDto.builder()
                .pageNum(pageNum)
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .reviewPerStoreList(reviewList)
                .build();

    }
}
