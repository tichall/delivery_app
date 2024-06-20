package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.store.entity.Store;

public class RegisterStoreResponseDto {

    private String storeName;
    private String storeAddress;
    private String storeRegistrationNumber;
    private String storeInfo;

    public RegisterStoreResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.storeRegistrationNumber = store.getStoreRegistrationNumber();
        this.storeInfo = store.getStoreInfo();
    }
}

