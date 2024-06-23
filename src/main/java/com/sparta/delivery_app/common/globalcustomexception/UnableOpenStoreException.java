package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalDuplicatedException;

public class UnableOpenStoreException extends GlobalDuplicatedException {

    public UnableOpenStoreException(ErrorCode errorCode) {
        super(errorCode);
    }
}
