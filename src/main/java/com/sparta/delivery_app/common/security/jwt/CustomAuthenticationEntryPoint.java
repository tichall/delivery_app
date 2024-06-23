package com.sparta.delivery_app.common.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.JwtPropertiesErrorCode;
import com.sparta.delivery_app.common.globalResponse.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "인증 예외 필터")
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Jwt 인증 도중 예외 발생");
        String exception = (String) request.getAttribute("jwtException");

        ErrorCode errorCode = JwtPropertiesErrorCode.fromJwtProperties(exception);

        int statusCode = HttpStatus.FORBIDDEN.value();

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        String body = objectMapper.writeValueAsString(errorResponse);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(body);
    }

}