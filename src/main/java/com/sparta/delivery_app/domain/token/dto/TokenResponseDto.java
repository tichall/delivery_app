package com.sparta.delivery_app.domain.token.dto;

import com.sparta.delivery_app.common.security.jwt.dto.TokenDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;

    public static TokenResponseDto of(String email, TokenDto tokenDto) {
        return TokenResponseDto.builder()
                .email(email)
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
    }
}