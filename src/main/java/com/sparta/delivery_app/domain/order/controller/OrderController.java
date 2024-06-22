package com.sparta.delivery_app.domain.order.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.order.dto.request.OrderAddRequestDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderAddResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderGetResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderPageResponseDto;
import com.sparta.delivery_app.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<RestApiResponse<OrderAddResponseDto>> orderAdd(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @Valid @RequestBody final OrderAddRequestDto requestDto)
    {
        OrderAddResponseDto responseDto = orderService.addOrder(authenticationUser, requestDto);

        return ResponseEntity.status(StatusCode.CREATED.getCode())
                .body(RestApiResponse.of(StatusCode.CREATED.getCode(),"주문이 완료되었습니다.", responseDto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<OrderGetResponseDto>> orderDetails(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @PathVariable Long orderId)
    {
        OrderGetResponseDto responseDto = orderService.findOrder(authenticationUser, orderId);

        return ResponseEntity.status(StatusCode.OK.getCode())
                .body(RestApiResponse.of(responseDto));
    }

    @GetMapping
    public ResponseEntity<RestApiResponse<OrderPageResponseDto>> orderPage(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc)
    {

        OrderPageResponseDto responseDto = orderService.findOrders(authenticationUser,pageNum, sortBy, isDesc);

        return ResponseEntity.status(StatusCode.OK.getCode())
                .body(RestApiResponse.of(responseDto));
    }
}
