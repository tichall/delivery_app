package com.sparta.delivery_app.common.security.errorcode;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
    LOGIN_FAIL(StatusCode.BAD_REQUEST.getCode(), "로그인 실패하였습니다.");

    private final Integer httpStatusCode;
    private final String description;
}

