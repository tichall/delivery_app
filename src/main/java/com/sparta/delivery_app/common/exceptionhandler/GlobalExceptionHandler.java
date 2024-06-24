package com.sparta.delivery_app.common.exceptionhandler;

import com.sparta.delivery_app.common.exception.errorcode.CommonErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalResponse.ErrorResponse;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 서버 500에러 발생
     */
    @ExceptionHandler(GlobalServerException.class)
    protected ResponseEntity<ErrorResponse> globalServerException(GlobalServerException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

//    /**
//     * 요청 정보가 잘 못된 경우
//     */
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
//        log.error("{}", e.getMessage());
//        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.BAD_REQUEST);
//
//        return ResponseEntity.status(errorResponse.getCode())
//                .body(errorResponse);
//    }
//
//    /**
//     * 지원하지 않은 HTTP method 호출 할 경우 발생
//     */
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        log.error("{}", e.getMessage());
//        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.METHOD_NOT_ALLOWED);
//
//        return ResponseEntity.status(errorResponse.getCode())
//                .body(errorResponse);
//    }
//
//    /**
//     * 지원하지 않는 API 요청인 경우
//     */
//    @ExceptionHandler(NoResourceFoundException.class)
//    protected ResponseEntity handleNoResourceFoundException(NoResourceFoundException e) {
//        log.error("[handleNoResourceFoundException] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
//        log.error("{}", e.getMessage());
//        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.NO_RESOURCE_FOUND_EXCEPTION);
//
//        return ResponseEntity.status(errorResponse.getCode())
//                .body(errorResponse);
//    }
//
//    /**
//     * 하위에서 잡지 못한 에러
//     */
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity handleException(Exception e) {
//        log.error("[handleException] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
//        log.error("{}", e.getMessage());
//
//        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.INTERNAL_SERVER_ERROR);
//
//        return ResponseEntity.status(errorResponse.getCode())
//                .body(errorResponse);
//    }

}
