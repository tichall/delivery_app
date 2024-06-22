package com.sparta.delivery_app.domain.admin.adminstore;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageMenuPerStoreResponseDto {

    private final Long storeId;
    private final String storeName;
    private final List<MenuPerStoreResponseDto> menuPerStoreList;

    public static PageMenuPerStoreResponseDto of(Integer pageNum,Store store) {
        List<Order> orderList = store.getOrderList();

        List<MenuPerStoreResponseDto> menuDtoList = store.getMenuList().stream()
                .map(menu -> {
                    Long totalOrderCountPerMenu = calculateTotalOrderCount(orderList, menu);
                    return MenuPerStoreResponseDto.of(menu, totalOrderCountPerMenu);
                })
                .toList();

        return new PageMenuPerStoreResponseDto(
                store.getId(),
                store.getStoreName(),
                menuDtoList
        );
    }

    private static Long calculateTotalOrderCount(List<Order> orderList, Menu menu) {
        return orderList.stream()
                .filter(order -> order.getOrderItemList().stream()
                        .anyMatch(item -> item.getMenu().equals(menu)))
                .count();
    }

}