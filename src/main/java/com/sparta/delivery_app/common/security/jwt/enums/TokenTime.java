package com.sparta.delivery_app.common.security.jwt.enums;

import com.sparta.delivery_app.common.exception.errorcode.CommonErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalServerException;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenTime {
    CONSUMER_TOKEN_TIME(Authority.CONSUMER, 1000 * 60 * 60 * 2, 1000 * 60 * 60 * 24 * 3), // 2시간, 3일
    MANAGER_TOKEN_TIME(Authority.MANAGER, 1000 * 60 * 60 * 24, 1000 * 60 * 60 * 24), // 1일, 1일
    ADMIN_TOKEN_TIME(Authority.ADMIN, 1000 * 60 * 60, 1000 * 60 * 60); // 1시간, 1시간

    private final String authority;
    private final long accessTimeInMillis;
    private final long refreshTimeInMillis;

    public static TokenTime checkUserRole(UserRole userRole) {
        for (TokenTime value : TokenTime.values()) {
            if (value.authority.equalsIgnoreCase(userRole.getUserRoleName())) {
                return value;
            }
        }
        throw new GlobalServerException(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    public static class Authority {
        public static final String CONSUMER = "CONSUMER";
        public static final String MANAGER = "MANAGER";
        public static final String ADMIN = "ADMIN";
    }
}
