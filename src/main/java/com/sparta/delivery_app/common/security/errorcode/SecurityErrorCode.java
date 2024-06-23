package com.sparta.delivery_app.common.security.errorcode;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
    INVALID_JWT_SIGNATURE(StatusCode.BAD_REQUEST.getCode(), "유효하지 않는 토큰입니다."),
    EXPIRED_JWT_TOKEN(StatusCode.BAD_REQUEST.getCode(), "만료된 토큰입니다."),
    NOT_FOUND_TOKEN(StatusCode.BAD_REQUEST.getCode(), "토큰을 찾을 수 없습니다."),
    RESIGN_USER(StatusCode.BAD_REQUEST.getCode(), "탈퇴한 유저입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}

