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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping
    public ResponseEntity<RestApiResponse<OrderAddResponseDto>> orderAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody final OrderAddRequestDto requestDto) {
        OrderAddResponseDto responseDto = orderService.addOrder(user, requestDto);

        return ResponseEntity.status(StatusCode.CREATED.getCode())
                .body(RestApiResponse.of(StatusCode.CREATED.getCode(),"주문이 완료되었습니다.", responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<OrderGetResponseDto>> orderDetails(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long orderId) {
        OrderGetResponseDto responseDto = orderService.findOrder(user, orderId);

        return ResponseEntity.status(StatusCode.OK.getCode())
                .body(RestApiResponse.of(responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping
    public ResponseEntity<RestApiResponse<OrderPageResponseDto>> orderPage(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") final String sortBy,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc) {

        OrderPageResponseDto responseDto = orderService.findOrders(user, pageNum, sortBy, isDesc);

        return ResponseEntity.status(StatusCode.OK.getCode())
                .body(RestApiResponse.of(responseDto));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{orderId}/prepare")
    public ResponseEntity<RestApiResponse<Void>> orderPrepare(@PathVariable final Long orderId, @AuthenticationPrincipal AuthenticationUser user) {
        orderService.changeStatusPrepare(orderId, user);
        return ResponseEntity.status(StatusCode.OK.getCode())
                .body(RestApiResponse.of("주문을 조리 중입니다."));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{orderId}/delivered")
    public ResponseEntity<RestApiResponse<Void>> orderDelivered(@PathVariable final Long orderId, @AuthenticationPrincipal AuthenticationUser user) {
        orderService.changeStatusDelivered(orderId, user);
        return ResponseEntity.status(StatusCode.OK.getCode())
                .body(RestApiResponse.of("주문이 배달 완료되었습니다."));
    }
}
