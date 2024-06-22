package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtPropertiesErrorCode implements ErrorCode {
    INVALID_TOKEN(StatusCode.BAD_REQUEST.getCode(), "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT_TOKEN(StatusCode.FORBIDDEN.getCode(), "만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN(StatusCode.BAD_REQUEST.getCode(), "지원되지 않는 JWT 토큰 입니다."),
    JWT_CLAIMS_IS_EMPTY(StatusCode.BAD_REQUEST.getCode(), "잘못된 JWT 토큰 입니다."),
    TOKEN_NOT_FOUND(StatusCode.FORBIDDEN.getCode(), "토큰을 찾을 수 없습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

    public static JwtPropertiesErrorCode fromJwtProperties(String fileExtension) {
        JwtPropertiesErrorCode jwtPropertiesErrorCode = TOKEN_NOT_FOUND; //Default message
        for (JwtPropertiesErrorCode value : values()) {
            if (value.getDescription().equalsIgnoreCase(fileExtension)) {
                jwtPropertiesErrorCode = value;
            }
        }
        return jwtPropertiesErrorCode;
    }
}