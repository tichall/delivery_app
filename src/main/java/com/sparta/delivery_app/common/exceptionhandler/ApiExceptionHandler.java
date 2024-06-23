package com.sparta.delivery_app.common.exceptionhandler;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalResponse.ErrorResponse;
import com.sparta.delivery_app.common.globalcustomexception.global.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "ApiException")
@Order(1)
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Api 요청에 동작 중 예외가 발생한 경우
     */
    @ExceptionHandler(GlobalDuplicatedException.class)
    protected ResponseEntity<ErrorResponse> globalDuplicatedException(GlobalDuplicatedException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(GlobalNotFoundException.class)
    protected ResponseEntity<ErrorResponse> globalNotFoundException(GlobalNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(GlobalAccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> globalAccessDeniedException(GlobalAccessDeniedException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(GlobalMismatchException.class)
    protected ResponseEntity<ErrorResponse> globalMismatchException(GlobalMismatchException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(GlobalStatusException.class)
    protected ResponseEntity<ErrorResponse> globalStatusException(GlobalStatusException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }


}
