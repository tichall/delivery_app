package com.sparta.delivery_app.domain.order.dto.response;

import com.sparta.delivery_app.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class OrderPageResponseDto {
    private Integer currentPage;
    private Long totalOrder;
    private List<OrderGetResponseDto> orderList;

    public static OrderPageResponseDto of(Integer currentPage, Page<Order> orderPage) {
        return OrderPageResponseDto.builder()
                .currentPage(currentPage)
                .totalOrder(orderPage.getTotalElements())
                .orderList(orderPage.stream().map(OrderGetResponseDto::of).toList())
                .build();
    }
}
