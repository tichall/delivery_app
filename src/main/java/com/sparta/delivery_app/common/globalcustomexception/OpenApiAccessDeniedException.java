package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalAccessDeniedException;

public class OpenApiAccessDeniedException extends GlobalAccessDeniedException {
    public OpenApiAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
