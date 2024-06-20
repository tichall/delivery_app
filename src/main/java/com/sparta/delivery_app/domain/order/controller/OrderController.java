package com.sparta.delivery_app.domain.order.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.order.dto.request.OrderAddRequestDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderAddResponseDto;
import com.sparta.delivery_app.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<RestApiResponse<OrderAddResponseDto>> orderAdd(@RequestBody OrderAddRequestDto requestDto) {
        OrderAddResponseDto responseDto = orderService.addOrder(requestDto);

        return ResponseEntity.status(StatusCode.CREATED.getCode())
                .body(RestApiResponse.of("주문이 완료되었습니다.", responseDto));
    }
}
