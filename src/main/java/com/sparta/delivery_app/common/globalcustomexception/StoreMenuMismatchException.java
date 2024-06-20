package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class StoreMenuMismatchException extends GlobalMismatchException {
    public StoreMenuMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
