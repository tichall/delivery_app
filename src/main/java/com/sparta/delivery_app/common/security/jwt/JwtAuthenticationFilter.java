package com.sparta.delivery_app.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery_app.common.globalResponse.ErrorResponse;
import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.errorcode.SecurityErrorCode;
import com.sparta.delivery_app.common.security.exception.CustomSecurityException;
import com.sparta.delivery_app.common.security.jwt.dto.AuthRequestDto;
import com.sparta.delivery_app.common.security.jwt.dto.TokenDto;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.sparta.delivery_app.common.exception.errorcode.UserErrorCode.NOT_FOUND_USER;
import static com.sparta.delivery_app.common.security.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.sparta.delivery_app.common.security.jwt.JwtConstants.REFRESH_TOKEN_HEADER;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final UserAdapter userAdapter;


    public JwtAuthenticationFilter(ObjectMapper objectMapper, JwtUtil jwtUtil, UserAdapter userAdapter
    ) {
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.userAdapter = userAdapter;
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), AuthRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error("attemptAuthentication 예외 발생 {} ", e.getMessage());
            throw new RuntimeException(e.getMessage());
//            throw new SecurityFilterException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 토큰 발행");

        User user = userAdapter.queryUserByEmail(authResult.getName());

        if (user.getUserStatus() == UserStatus.DISABLE) {
            request.setAttribute("exception", new CustomSecurityException(SecurityErrorCode.RESIGN_USER));
            throw new CustomSecurityException(SecurityErrorCode.RESIGN_USER);
        }

        TokenDto tokenDto = jwtUtil.generateAccessTokenAndRefreshToken(user.getEmail(), user.getUserRole());
        String refreshTokenValue = tokenDto.getRefreshToken().substring(7);
        user.updateRefreshToken(refreshTokenValue);

        userAdapter.saveUser(user);

        loginSuccessResponse(response, tokenDto);
    }


    private void loginSuccessResponse(HttpServletResponse response, TokenDto tokenDto) throws IOException {
        RestApiResponse apiResponse = RestApiResponse.of("로그인 성공");
        String body = objectMapper.writeValueAsString(apiResponse);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader(ACCESS_TOKEN_HEADER, tokenDto.getAccessToken());
        response.addHeader(REFRESH_TOKEN_HEADER, tokenDto.getRefreshToken());
        response.getWriter().write(body);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("unsuccessfulAuthentication | 로그인 실패");
        String body = objectMapper.writeValueAsString(ErrorResponse.of(NOT_FOUND_USER));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);
    }
}