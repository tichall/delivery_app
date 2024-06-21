package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;

public class PageNotFoundException extends GlobalNotFoundException{
    public PageNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
