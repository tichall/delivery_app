package com.sparta.delivery_app.common.security.jwt;

import com.sparta.delivery_app.common.security.jwt.dto.TokenDto;
import com.sparta.delivery_app.common.security.jwt.enums.TokenTime;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static com.sparta.delivery_app.common.security.jwt.JwtConstants.*;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    @Value("${jwt.secret-key}") // Base64 Encode 한 SecretKey
    private String secret_key;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secret_key);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenDto generateAccessTokenAndRefreshToken(String username, UserRole userRole) {
        TokenTime tokenTime = TokenTime.checkUserRole(userRole);
        String accessToken = createAccessToken(username, userRole, tokenTime.getAccessTimeInMillis());
        String refreshToken = createRefreshToken(username, userRole, tokenTime.getRefreshTimeInMillis());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // access Token 생성
    public String createAccessToken(String email, UserRole userRole, long tokenTime) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setId(UUID.randomUUID().toString()) // JWT ID 설정
                        .setSubject(email) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, userRole) // 사용자 권한
                        .setIssuedAt(new Date(date.getTime())) // 생성시간
                        .setExpiration(new Date(date.getTime() + tokenTime)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .claim("tokenType", ACCESS_TOKEN_TYPE)
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // refresh Token 생성
    public String createRefreshToken(String username, UserRole userRole, long tokenTime) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setId(UUID.randomUUID().toString()) // JWT ID 설정
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, userRole) // 사용자 권한
                        .setIssuedAt(new Date(date.getTime())) // 생성시간
                        .setExpiration(new Date(date.getTime() + tokenTime)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .claim("tokenType", REFRESH_TOKEN_TYPE)
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // header 에서 access token
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // header 에서 refresh token
    public String getRefreshTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(HttpServletRequest request, String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명", e);
            request.setAttribute("exception", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token", e);
            request.setAttribute("exception", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰", e);
            request.setAttribute("exception", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰", e);
            request.setAttribute("exception", e);
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}