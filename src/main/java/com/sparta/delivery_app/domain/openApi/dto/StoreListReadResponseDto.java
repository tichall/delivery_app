package com.sparta.delivery_app.domain.openApi.dto;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class StoreListReadResponseDto {

    private String storeName;
    private String storeAddress;
    private Long minTotalPrice;
    private String storeInfo;
    private Integer totalStoreLiked;


    public static StoreListReadResponseDto of(Store store) {
        return StoreListReadResponseDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .minTotalPrice(store.getMinTotalPrice())
                .storeInfo(store.getStoreInfo())
                .totalStoreLiked(store.getStoreLikedList().size())
                .build();
    }
}
