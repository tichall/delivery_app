package com.sparta.delivery_app.domain.admin.adminstore.dto;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageReviewPerStoreResponseDto {

    private final Integer currentPage;
    private final String totalReview;
    private final Long storeId;
    private final String storeName;
    private final List<ReviewPerStoreResponseDto> reviewPerStoreList;

    public static PageReviewPerStoreResponseDto of(
            Integer currentPage, String totalReview, Store store, Page<ReviewPerStoreResponseDto> reviewPage) {

        return PageReviewPerStoreResponseDto.builder()
                .currentPage(currentPage)
                .totalReview(totalReview)
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .reviewPerStoreList(reviewPage.getContent().stream().toList())
                .build();

    }
}
