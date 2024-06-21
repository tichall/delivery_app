package com.sparta.delivery_app.common.globalcustomexception.global;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public GlobalNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
