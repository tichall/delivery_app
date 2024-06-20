package com.sparta.delivery_app.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderAddRequestDto {
    @NotBlank(message = "매장 아이디가 입력되지 않았습니다.")
    private Long storeId;

    @NotBlank(message = "주문할 메뉴가 입력되지 않았습니다.")
    private List<MenuItemRequestDto> menuItemRequestDtoList;
}
