package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class OrderNotFoundException extends GlobalNotFoundException{
    public OrderNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
