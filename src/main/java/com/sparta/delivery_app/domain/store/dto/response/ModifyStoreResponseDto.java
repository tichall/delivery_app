package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;

@Builder
public class ModifyStoreResponseDto {

    private String storeName;
    private String storeAddress;
    private String storeRegistrationNumber;
    private Long minTotalPrice;
    private String storeInfo;

    public static ModifyStoreResponseDto of (Store store){
        return ModifyStoreResponseDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .storeRegistrationNumber(store.getStoreRegistrationNumber())
                .minTotalPrice(store.getMinTotalPrice())
                .storeInfo(store.getStoreInfo())
                .build();
    }

}



