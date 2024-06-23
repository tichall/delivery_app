package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalDuplicatedException;

public class ReviewDuplicatedException extends GlobalDuplicatedException {
    public ReviewDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
