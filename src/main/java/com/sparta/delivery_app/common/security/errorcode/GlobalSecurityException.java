package com.sparta.delivery_app.common.security.errorcode;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public interface GlobalSecurityException {
    ErrorCode getErrorCode();

    String getErrorDescription();
}
