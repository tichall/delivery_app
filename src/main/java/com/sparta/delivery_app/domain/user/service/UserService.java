package com.sparta.delivery_app.domain.user.service;

import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdaptor userAdaptor;

    public ConsumersSignupResponseDto userAdd(UserSignupRequestDto requestDto) {
        userAdaptor.checkDuplicateEmail(requestDto.getEmail());

        User userData = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .userAddress(requestDto.getAddress())
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.USER)
                .build();
        userAdaptor.saveUser(userData);
        return ConsumersSignupResponseDto.of(userData);
    }
}
