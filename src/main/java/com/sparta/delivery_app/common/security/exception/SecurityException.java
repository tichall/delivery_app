package com.sparta.delivery_app.common.security.exception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.security.errorcode.GlobalSecurityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SecurityException extends RuntimeException implements GlobalSecurityException {
    private final ErrorCode errorCode;
    private final String errorDescription;
}
