package com.sparta.delivery_app.domain.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record MenuItemRequestDto (
        @NotNull(message = "메뉴 아이디가 입력되지 않았습니다.")
        Long menuId,

        @NotNull(message = "메뉴 수량이 입력되지 않았습니다.")
        @Min(value = 1, message = "1개 이상 주문해주세요.")
        Integer quantity
){
}
