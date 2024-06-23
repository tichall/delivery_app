package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode{
    INVALID_REVIEW(StatusCode.NOT_FOUND.getCode(), "해당 리뷰를 조회할 수 없습니다."),
    NOT_AUTHORITY_TO_UPDATE_REVIEW(StatusCode.FORBIDDEN.getCode(), "수정 권한이 없습니다."),
    NOT_AUTHORITY_TO_DELETE_REVIEW(StatusCode.FORBIDDEN.getCode(), "삭제 권한이 없습니다."),
    DELETED_REVIEW(StatusCode.BAD_REQUEST.getCode(), "삭제된 리뷰입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
