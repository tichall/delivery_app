package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

    INVALID_MENU(StatusCode.NOT_FOUND.getCode(), "해당 메뉴를 조회할 수 없습니다."),
    DELETED_MENU(StatusCode.BAD_REQUEST.getCode(), "삭제된 메뉴입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
