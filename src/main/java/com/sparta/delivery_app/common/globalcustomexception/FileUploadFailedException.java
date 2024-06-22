package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class FileUploadFailedException extends RuntimeException {
    private final ErrorCode errorCode;

    public FileUploadFailedException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}