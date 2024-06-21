package com.sparta.delivery_app.common.security.jwt;

import com.sparta.delivery_app.common.exception.errorcode.JwtPropertiesErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.TokenNotFoundException;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final UserAdaptor userAdaptor;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("로그아웃 시도");

        String accessTokenValue = jwtUtil.getAccessTokenFromHeader(request);
        String refreshTokenValue = jwtUtil.getRefreshTokenFromHeader(request);

        if (accessTokenValue == null && refreshTokenValue == null) {
            log.error("로그아웃 시도 중 에러 발생");
            throw new TokenNotFoundException(JwtPropertiesErrorCode.TOKEN_NOT_FOUND);
        }
        String email = jwtUtil.getUserInfoFromToken(refreshTokenValue).getSubject();
        User findUser = userAdaptor.queryUserByEmail(email);
        findUser.updateRefreshToken(null);
        userAdaptor.saveUser(findUser);

        SecurityContextHolder.clearContext();
    }
}