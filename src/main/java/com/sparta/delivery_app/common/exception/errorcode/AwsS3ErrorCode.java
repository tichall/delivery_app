package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum AwsS3ErrorCode implements ErrorCode {
    IMAGE_UPLOAD_ERROR(StatusCode.INTERNAL_SERVER_ERROR.getCode(), "이미지 업로드 중 오류가 발생했습니다.");

    private final Integer httpStatusCode;
    private final String description;
}
