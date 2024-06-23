package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageErrorCode implements ErrorCode {
    INVALID_PAGE_NUMBER(StatusCode.BAD_REQUEST.getCode(), "접근할 수 없는 페이지입니다."),
    UNABLE_TO_CONNECT(StatusCode.BAD_REQUEST.getCode(), "접속할 수 없습니다. 잠시 후 시도해주세요.");

    private final Integer httpStatusCode;
    private final String description;
}
