package com.sparta.delivery_app.domain.user.dto.response;

import com.sparta.delivery_app.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileReadResponseDto {
    private String email;
    private String nickName;
    private String name;
    private String address;
    private Long totalLikedStores;
    private Long totalLikedUserReviews;

    public static UserProfileReadResponseDto of(User user, Long totalLikedStores, Long totalLikedUserReviews) {
        return UserProfileReadResponseDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .name(user.getName())
                .address(user.getUserAddress())
                .totalLikedStores(totalLikedStores)
                .totalLikedUserReviews(totalLikedUserReviews)
                .build();
    }
}
