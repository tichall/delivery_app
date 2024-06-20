package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {
    MENU_STATUS_ERROR(StatusCode.BAD_REQUEST.getCode(), "판매 중인 메뉴가 아닙니다."),
    MENU_NOT_FOUND(StatusCode.NOT_FOUND.getCode(), "메뉴가 존재하지 않습니다.");

    private final Integer httpStatusCode;
    private final String description;
}
