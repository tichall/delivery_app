package com.sparta.delivery_app.domain.user.entity;

import com.sparta.delivery_app.common.exception.errorcode.UserErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.UserStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    private final String userStatusName;

    public static void checkUserStatus(UserStatus userRole) {
        if (userRole == DISABLE) {
            throw new UserStatusException(UserErrorCode.USER_STATUS_DISABLE);
        }
    }
}
