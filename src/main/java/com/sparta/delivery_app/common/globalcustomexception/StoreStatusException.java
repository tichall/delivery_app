package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class StoreStatusException extends GlobalStatusException {
    public StoreStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
