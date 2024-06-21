package com.sparta.delivery_app.common.security.jwt.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {
    private String email;
    private String password;
}