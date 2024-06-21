package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterStoreResponseDto {

    private String storeName;
    private String storeAddress;
    private String storeRegistrationNumber;
    private Long minTotalPrice;
    private String storeInfo;

    public static RegisterStoreResponseDto of (Store store){
        return RegisterStoreResponseDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .storeRegistrationNumber(store.getStoreRegistrationNumber())
                .minTotalPrice(store.getMinTotalPrice())
                .storeInfo(store.getStoreInfo())
                .build();
    }
}

