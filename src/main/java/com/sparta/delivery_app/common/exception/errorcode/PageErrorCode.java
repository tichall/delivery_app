package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageErrorCode implements ErrorCode {
    INVALID_PAGE_NUMBER(StatusCode.BAD_REQUEST.getCode(), "접근할 수 없는 페이지입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
