package com.sparta.delivery_app.domain.user.service;

import com.sparta.delivery_app.common.exception.errorcode.UserErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.UserPasswordMismatchException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.dto.request.ConsumersSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.request.UserResignRequestDto;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdaptor userAdaptor;
    private final PasswordEncoder passwordEncoder;

    public ConsumersSignupResponseDto ConsumersUserAdd(ConsumersSignupRequestDto requestDto) {
        userAdaptor.checkDuplicateEmail(requestDto.getEmail());

        User userData = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .nickName(requestDto.getNickName())
                .userAddress(requestDto.getAddress())
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.CONSUMER)
                .build();
        userAdaptor.saveUser(userData);
        return ConsumersSignupResponseDto.of(userData);
    }

    public ConsumersSignupResponseDto managersUserAdd(ConsumersSignupRequestDto requestDto) {
        userAdaptor.checkDuplicateEmail(requestDto.getEmail());

        User userData = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .nickName(requestDto.getNickName())
                .userAddress(requestDto.getAddress())
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.MANAGER)
                .build();
        userAdaptor.saveUser(userData);
        return ConsumersSignupResponseDto.of(userData);
    }

    @Transactional
    public void resignUser(AuthenticationUser user, UserResignRequestDto userResignRequestDto) {
        String usercode = user.getUsername();

        User findUser = userAdaptor.queryUserByEmail(usercode);

        if (!passwordEncoder.matches(userResignRequestDto.getPassword(), findUser.getPassword())) {
            throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        UserStatus.checkUserStatus(findUser.getUserStatus());

        findUser.updateUserStatus();
    }
}
