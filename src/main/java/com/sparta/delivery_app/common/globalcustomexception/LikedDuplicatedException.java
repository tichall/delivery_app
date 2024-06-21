package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class LikedDuplicatedException extends GlobalDuplicatedException{
    public LikedDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
