package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class UserNotExistException extends GlobalDuplicatedException {

    public UserNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }

}
