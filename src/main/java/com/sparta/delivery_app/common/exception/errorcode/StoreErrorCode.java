package com.sparta.delivery_app.common.exception.errorcode;


import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {

    INVALID_STORE(StatusCode.NOT_FOUND.getCode(), "해당 매장을 조회할 수 없습니다.");

    private final Integer httpStatusCode;
    private final String description;
}
