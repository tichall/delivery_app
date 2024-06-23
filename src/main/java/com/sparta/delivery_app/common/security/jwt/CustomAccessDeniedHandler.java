package com.sparta.delivery_app.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery_app.common.exception.errorcode.CommonErrorCode;
import com.sparta.delivery_app.common.globalResponse.ErrorResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.AUTH_USER_FORBIDDEN);

        String body = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(StatusCode.FORBIDDEN.code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
