package com.sparta.delivery_app.domain.order.dto.response;

import com.sparta.delivery_app.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderGetResponseDto {
    private String storeName;
    private List<MenuItemResponseDto> menuList;
    private Long totalPrice;
    private String orderStatus;

    public static OrderGetResponseDto of(Order order) {
        return OrderGetResponseDto.builder()
                .storeName(order.getStore().getStoreName())
                .menuList(order.getOrderItemList().stream().map(MenuItemResponseDto::of).toList())
                .totalPrice(order.calculateTotalPrice())
                .orderStatus(order.getOrderStatus().getOrderStatusName())
                .build();
    }

}
