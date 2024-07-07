package com.sparta.delivery_app.domain.openApi.dto;

import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailsResponseDto {

    private String storeName;
    private String storeAddress;
    private String storeRegistrationNumber;
    private Long minTotalPrice;
    private String storeInfo;
    private Integer totalStoreLiked;
    private List<MenuListReadResponseDto> menuList;

    public StoreDetailsResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.storeRegistrationNumber = store.getStoreRegistrationNumber();
        this.minTotalPrice = store.getMinTotalPrice();
        this.storeInfo = store.getStoreInfo();
        this.totalStoreLiked = store.getStoreLikedList().stream().filter(a -> a.getIsLiked().equals(true)).toList().size();
        // 해당 매장의 메뉴 중, ENABLE 상태인 메뉴만 필터링하여 메뉴 리스트 생성
        this.menuList = store.getMenuList().stream()
                .filter(b -> b.getMenuStatus().equals(MenuStatus.ENABLE))
                .map(MenuListReadResponseDto::new)
                .toList();
    }

//    @Builder
//    public StoreDetailsResponseDto(String storeName, String storeAddress, String storeRegistrationNumber, Long minTotalPrice, String storeInfo, Long totalStoreLiked, List<MenuListReadResponseDto> menuList) {
//        this.storeName = storeName;
//        this.storeAddress = storeAddress;
//        this.storeRegistrationNumber = storeRegistrationNumber;
//        this.minTotalPrice = minTotalPrice;
//        this.storeInfo = storeInfo;
//        this.totalStoreLiked = totalStoreLiked;
//        this.menuList = menuList;
//    }
}
