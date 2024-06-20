package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class StoreNotFoundException extends GlobalNotFoundException {
    public StoreNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
