package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class UserOrderMismatchException extends GlobalMismatchException{
    public UserOrderMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
