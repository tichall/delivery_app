package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Getter;


@Getter
public class StoreListReadResponseDto {

    private String storeName;
    private String storeAddress;
    private String storeRegistrationNumber;
    private Long minTotalPrice;
    private String storeInfo;

    public StoreListReadResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.storeRegistrationNumber = store.getStoreRegistrationNumber();
        this.minTotalPrice = store.getMinTotalPrice();
        this.storeInfo = store.getStoreInfo();
    }
}
