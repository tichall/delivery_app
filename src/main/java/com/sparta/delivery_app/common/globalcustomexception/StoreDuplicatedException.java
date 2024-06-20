package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class StoreDuplicatedException extends GlobalDuplicatedException {

    public StoreDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
