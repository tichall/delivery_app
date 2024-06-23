package com.sparta.delivery_app.common.globalcustomexception.global;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalServerException extends RuntimeException {
    private final ErrorCode errorCode;

    public GlobalServerException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
