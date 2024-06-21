package com.sparta.delivery_app.domain.openApi.dto;

import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailsResponseDto {

    private String storeName;
    private String storeAddress;
    private String storeRegistrationNumber;
    private Long minTotalPrice;
    private String storeInfo;
    private List<MenuListReadResponseDto> menuList;

    public StoreDetailsResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.storeRegistrationNumber = store.getStoreRegistrationNumber();
        this.minTotalPrice = store.getMinTotalPrice();
        this.storeInfo = store.getStoreInfo();
        // 해당 매장의 메뉴 중, ENABLE 상태인 메뉴만 필터링하여 메뉴 리스트 생성
        this.menuList = store.getMenuList().stream()
                .filter(b -> b.getMenuStatus().equals(MenuStatus.ENABLE))
                .map(MenuListReadResponseDto::new)
                .toList();
    }
}
