package com.sparta.delivery_app.domain.order.dto.response;

import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderAddResponseDto {
    private String storeName;
    private List<MenuItemResponseDto> menuItemResponseDtoList;
    private Long totalPrice;
    private OrderStatus orderStatus;

    public static OrderAddResponseDto of(Order order, Long totalPrice) {
        return OrderAddResponseDto.builder()
                .storeName(order.getStore().getStoreName())
                .menuItemResponseDtoList(order.getOrderItemList().stream().map(MenuItemResponseDto::of).toList())
                .totalPrice(totalPrice)
                .build();
    }
}
