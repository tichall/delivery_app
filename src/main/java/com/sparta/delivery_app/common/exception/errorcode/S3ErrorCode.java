package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {
    IMAGE_STREAM_ERROR(StatusCode.INTERNAL_SERVER_ERROR.getCode(), "이미지 업로드 중 오류가 발생했습니다."),
    INVALID_EXTENSION(StatusCode.BAD_REQUEST.getCode(), "첨부할 수 없는 파일 확장자입니다."),
    INVALID_IMAGE_URL(StatusCode.BAD_REQUEST.getCode(), "잘못된 이미지 URL입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
