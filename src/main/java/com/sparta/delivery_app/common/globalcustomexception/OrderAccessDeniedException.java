package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class OrderAccessDeniedException extends GlobalAccessDeniedException {
    public OrderAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
