package com.sparta.delivery_app.domain.user.dto.response;

import com.sparta.delivery_app.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileModifyResponseDto {
    private String nickName;
    private String name;
    private String address;

    public static UserProfileModifyResponseDto of(User user) {
        return UserProfileModifyResponseDto.builder()
                .nickName(user.getNickName())
                .name(user.getName())
                .address(user.getUserAddress())
                .build();
    }
}
