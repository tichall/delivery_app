package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    TOTAL_PRICE_ERROR(StatusCode.BAD_REQUEST.getCode(), "주문 금액이 최소 주문 금액 이상이 되어야 합니다.");

    private final Integer httpStatusCode;
    private final String description;
}
