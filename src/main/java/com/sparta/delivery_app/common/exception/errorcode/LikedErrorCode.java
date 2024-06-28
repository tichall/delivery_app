package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikedErrorCode implements ErrorCode{
    LIKED_ALREADY_REGISTERED_ERROR(StatusCode.BAD_REQUEST.getCode(), "이미 등록된 관심 매장입니다."),
    LIKED_UNREGISTERED_ERROR(StatusCode.BAD_REQUEST.getCode(), "등록되지 않은 관심 매장입니다."),
    SELF_LIKED_REGISTERED_ERROR(StatusCode.BAD_REQUEST.getCode(), "자신의 리뷰에는 좋아요를 남길 수 없습니다.");

    private final Integer httpStatusCode;
    private final String description;
}
