package com.sparta.delivery_app.domain.token.service;

import com.sparta.delivery_app.common.globalcustomexception.TokenNotFoundException;
import com.sparta.delivery_app.common.security.errorcode.SecurityErrorCode;
import com.sparta.delivery_app.common.security.exception.CustomSecurityException;
import com.sparta.delivery_app.common.security.jwt.JwtUtil;
import com.sparta.delivery_app.common.security.jwt.dto.TokenDto;
import com.sparta.delivery_app.domain.token.dto.TokenResponseDto;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final UserAdaptor userAdaptor;

    public String validateTokenExpire(HttpServletRequest request) {
        String accessTokenHeader = jwtUtil.getAccessTokenFromHeader(request);
        String refreshTokenHeader = jwtUtil.getRefreshTokenFromHeader(request);

        //access 또는 refresh가 없는 경우
        if (accessTokenHeader == null || refreshTokenHeader == null) {
            throw new TokenNotFoundException(SecurityErrorCode.NOT_FOUND_TOKEN);
        }

        //refresh token이 유효하지 않은 경우
        if (!jwtUtil.validateToken(request, refreshTokenHeader)) {
            throw new TokenNotFoundException(SecurityErrorCode.INVALID_JWT_SIGNATURE);
        }

        return refreshTokenHeader;
    }

    @Transactional
    public TokenResponseDto getFindUser(String refreshTokenHeader) {
        String email = jwtUtil.getUserInfoFromToken(refreshTokenHeader).getSubject();
        User user = userAdaptor.queryUserByEmailAndStatus(email);

        if (user.getRefreshToken() == null) {
            throw new TokenNotFoundException(SecurityErrorCode.NOT_FOUND_TOKEN);
        }

        TokenDto tokenDto = jwtUtil.generateAccessTokenAndRefreshToken(user.getEmail(), user.getUserRole());
        String refreshTokenValue = tokenDto.getRefreshToken().substring(7);
        user.updateRefreshToken(refreshTokenValue);

        return TokenResponseDto.of(user.getEmail(), tokenDto);

    }
}