package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    TOTAL_PRICE_ERROR(StatusCode.BAD_REQUEST.getCode(), "주문 금액이 최소 주문 금액 이상이 되어야 합니다."),
    STORE_MENU_MISMATCH(StatusCode.BAD_REQUEST.getCode(), "해당 매장의 메뉴가 아닙니다."),
    ORDER_NOT_FOUND(StatusCode.NOT_FOUND.getCode(), "해당 주문을 조회할 수 없습니다."),
    USER_ORDER_MISMATCH(StatusCode.BAD_REQUEST.getCode(), "조회 권한이 없는 주문 내역입니다.");


    private final Integer httpStatusCode;
    private final String description;
}
