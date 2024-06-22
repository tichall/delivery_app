package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class ReviewStatusException extends GlobalStatusException{
    public ReviewStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
