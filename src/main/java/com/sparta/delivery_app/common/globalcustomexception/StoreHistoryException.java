package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class StoreHistoryException extends GlobalDuplicatedException {

    public StoreHistoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
