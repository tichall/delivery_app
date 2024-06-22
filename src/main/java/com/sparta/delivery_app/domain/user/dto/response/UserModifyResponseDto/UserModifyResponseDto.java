package com.sparta.delivery_app.domain.user.dto.response.UserModifyResponseDto;

import com.sparta.delivery_app.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserModifyResponseDto {
    private String email;
    private String username;
    private String address;

    public static UserModifyResponseDto of(User user) {
        return UserModifyResponseDto.builder()
                .email(user.getEmail())
                .username(user.getName())
                .address(user.getUserAddress())
                .build();
    }
}
