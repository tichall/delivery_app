package com.sparta.delivery_app.common.globalcustomexception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalStatusException;

public class MenuStatusException extends GlobalStatusException {
    public MenuStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
