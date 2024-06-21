package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    DUPLICATED_USER(StatusCode.BAD_REQUEST.getCode(), "중복된 유저 정보입니다."),

    NOT_SIGNED_UP_USER(StatusCode.NOT_FOUND.getCode(), "회원가입 후 이용해 주세요."),

    NOT_USER(StatusCode.UNAUTHORIZED.getCode(), "매장 등록 권한이 없습니다."),
    NOT_FOUND_USER(StatusCode.NOT_FOUND.getCode(), "아이디 또는 비밀번호를 잘못 입력했습니다."),

    ;

    private final Integer httpStatusCode;
    private final String description;
}

