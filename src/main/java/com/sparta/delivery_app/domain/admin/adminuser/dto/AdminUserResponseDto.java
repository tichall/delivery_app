package com.sparta.delivery_app.domain.admin.adminuser.dto;

import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdminUserResponseDto {

    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String userAddress;
    private UserStatus userStatus;
    private UserRole userRole;
    private String pageInfo;

    public static AdminUserResponseDto of (User user) {
        return AdminUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .userAddress(user.getUserAddress())
                .userStatus(user.getUserStatus())
                .userRole(user.getUserRole())
                .build();
    }

    public AdminUserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.userAddress = user.getUserAddress();
        this.userStatus = user.getUserStatus();
        this.userRole = user.getUserRole();
    }

    public AdminUserResponseDto(User user, String pageInfo) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.userAddress = user.getUserAddress();
        this.userStatus = user.getUserStatus();
        this.userRole = user.getUserRole();
        this.pageInfo = pageInfo;
    }

}
