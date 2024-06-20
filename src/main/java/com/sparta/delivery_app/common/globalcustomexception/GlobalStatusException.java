package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalStatusException extends RuntimeException {
    private final ErrorCode errorCode;

    public GlobalStatusException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
