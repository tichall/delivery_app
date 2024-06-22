package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalNotFoundException;

public class ReviewNotFoundException extends GlobalNotFoundException {

    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
