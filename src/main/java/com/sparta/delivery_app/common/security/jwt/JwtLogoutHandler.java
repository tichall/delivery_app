package com.sparta.delivery_app.common.security.jwt;

import com.sparta.delivery_app.common.security.errorcode.SecurityErrorCode;
import com.sparta.delivery_app.common.security.exception.CustomSecurityException;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final UserAdapter userAdapter;

    /**
     * access refresh들고옴
     * 없다면 예외
     * 토큰이 db에 없다면
     * 토큰이 불일치 하다면
     * 회원상태가 탈퇴 상태라면
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("로그아웃 시도");
        String accessTokenFromHeader = jwtUtil.getAccessTokenFromHeader(request);
        String refreshTokenFromHeader = jwtUtil.getRefreshTokenFromHeader(request);
        if (accessTokenFromHeader == null || refreshTokenFromHeader == null) {
            log.error("찾을 수 없는 토큰");
            CustomSecurityException e = new CustomSecurityException(SecurityErrorCode.NOT_FOUND_TOKEN);
            request.setAttribute("exception", e);
            throw e;
        }
        String email = jwtUtil.getUserInfoFromToken(refreshTokenFromHeader).getSubject();
        User findUser = userAdapter.queryUserByEmailAndStatus(email);

        if (!Objects.equals(refreshTokenFromHeader, findUser.getRefreshToken())) {
            log.error("일치하지 않는 토큰");
            CustomSecurityException e = new CustomSecurityException(SecurityErrorCode.MISMATCH_TOKEN);
            request.setAttribute("exception", e);
            throw e;
        }
        findUser.updateRefreshToken(null);
        userAdapter.saveUser(findUser);

        SecurityContextHolder.clearContext();
    }

}