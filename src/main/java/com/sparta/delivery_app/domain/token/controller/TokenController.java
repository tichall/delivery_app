package com.sparta.delivery_app.domain.token.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.domain.token.dto.TokenResponseDto;
import com.sparta.delivery_app.domain.token.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sparta.delivery_app.common.security.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.sparta.delivery_app.common.security.jwt.JwtConstants.REFRESH_TOKEN_HEADER;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class TokenController {

    private final TokenService tokenService;

    // Access Token 만료 시 호출하는 API
    @PostMapping("/reissue")
    public ResponseEntity<RestApiResponse<TokenResponseDto>> refreshTokenReissue(HttpServletRequest request) {
        log.info("access token 재발급");
        String refreshTokenHeader = tokenService.validateTokenExpire(request);

        TokenResponseDto responseDto = tokenService.getFindUser(refreshTokenHeader);

        return ResponseEntity.ok()
                .header(ACCESS_TOKEN_HEADER, responseDto.getAccessToken())
                .header(REFRESH_TOKEN_HEADER, responseDto.getRefreshToken())
                .body(RestApiResponse.of(responseDto));

    }
}