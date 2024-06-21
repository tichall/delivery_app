package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    DUPLICATED_USER(StatusCode.BAD_REQUEST.getCode(), "중복된 유저 정보입니다."),

    NOT_SIGNED_UP_USER(StatusCode.NOT_FOUND.getCode(), "회원가입 후 이용해 주세요."),

    NOT_AUTHORITY_TO_REGISTER_STORE(StatusCode.UNAUTHORIZED.getCode(), "매장 등록 권한이 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final String description;
}

