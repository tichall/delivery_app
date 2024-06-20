package com.sparta.delivery_app.domain.order.dto.response;

import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuItemResponseDto {
    private String menuName;
    private Integer quantity;

    public static MenuItemResponseDto of(OrderItem orderItem) {
        return MenuItemResponseDto.builder()
                .menuName(orderItem.getMenu().getMenuName())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
