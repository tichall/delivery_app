package com.sparta.delivery_app.common.security.jwt;

import com.sparta.delivery_app.common.security.AuthenticationUserService;
import com.sparta.delivery_app.common.security.errorcode.SecurityErrorCode;
import com.sparta.delivery_app.common.security.exception.CustomSecurityException;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationUserService authenticationUserService;
    private final UserAdapter userAdapter;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthenticationUserService authenticationUserService, UserAdapter userAdapter) {
        this.jwtUtil = jwtUtil;
        this.authenticationUserService = authenticationUserService;
        this.userAdapter = userAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = jwtUtil.getAccessTokenFromHeader(req);

        log.info("access token 검증");
        if (StringUtils.hasText(accessTokenValue) && jwtUtil.validateToken(req, accessTokenValue)) {
            log.info("refresh token 검증");

            String email = jwtUtil.getUserInfoFromToken(accessTokenValue).getSubject();
            User findUser = userAdapter.queryUserByEmail(email);

            if (findUser.getRefreshToken() != null) {
                if (isValidateUserEmail(email, findUser)) {
                    log.info("Token 인증 완료");

                    Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                    setAuthentication(info.getSubject());
                }
            } else {
                log.error("유효하지 않는 Refresh Token");
                // 키값 문자열 - 객체 저장
                req.setAttribute("exception", new CustomSecurityException(SecurityErrorCode.INVALID_JWT_SIGNATURE));
            }
        }
        filterChain.doFilter(req, res);
    }

    private boolean isValidateUserEmail(String email, User findUser) {
        return email.equals(findUser.getEmail());
    }


    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = authenticationUserService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}