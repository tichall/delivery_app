package com.sparta.delivery_app.domain.user.dto.response;


import com.sparta.delivery_app.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagersSignupResponseDto {
    private String email;
    private String nickName;

    public static ManagersSignupResponseDto of(User user) {
        return ManagersSignupResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }
}
