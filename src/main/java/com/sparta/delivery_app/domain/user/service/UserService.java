package com.sparta.delivery_app.domain.user.service;

import com.sparta.delivery_app.common.exception.errorcode.UserErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.UserPasswordMismatchException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.user.adaptor.PasswordHistoryAdaptor;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.dto.request.*;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.ManagersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.UserProfileModifyResponseDto;
import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdaptor userAdaptor;
    private final PasswordHistoryAdaptor passwordHistoryAdaptor;
    private final PasswordEncoder passwordEncoder;

    public ConsumersSignupResponseDto consumersUserAdd(ConsumersSignupRequestDto requestDto) {
        userAdaptor.checkDuplicateEmail(requestDto.email());

        User userData = User.saveUser(requestDto);

        PasswordHistory passwordHistory = PasswordHistory.savePasswordHistory(
                userData, passwordEncoder.encode(requestDto.password())
        );

        userAdaptor.saveUser(userData);
        passwordHistoryAdaptor.savePasswordHistory(passwordHistory);

        return ConsumersSignupResponseDto.of(userData);
    }

    public ManagersSignupResponseDto managersUserAdd(ManagersSignupRequestDto requestDto) {
        userAdaptor.checkDuplicateEmail(requestDto.email());

        User userData = User.saveUser(requestDto);

        PasswordHistory passwordHistoryData = PasswordHistory.savePasswordHistory(
                userData, passwordEncoder.encode(requestDto.password())
        );

        userAdaptor.saveUser(userData);
        passwordHistoryAdaptor.savePasswordHistory(passwordHistoryData);
        return ManagersSignupResponseDto.of(userData);
    }

    @Transactional
    public void resignUser(AuthenticationUser user, UserResignRequestDto userResignRequestDto) {
        User findUser = userAdaptor.queryUserByEmail(user.getUsername());
        PasswordHistory passwordHistory = passwordHistoryAdaptor.queryPasswordHistoryTop1ByUser(findUser);

        if (!passwordEncoder.matches(userResignRequestDto.password(), passwordHistory.getPassword())) {
            log.error("기존 비밀번호와 불일치");
            throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        findUser.updateUserStatus();
    }

    @Transactional
    public UserProfileModifyResponseDto modifyProfileUser(AuthenticationUser user, UserProfileModifyRequestDto requestDto) {
        User findUser = userAdaptor.queryUserByEmail(user.getUsername());
        PasswordHistory passwordHistory = passwordHistoryAdaptor.queryPasswordHistoryTop1ByUser(findUser);

        if (!passwordEncoder.matches(requestDto.password(), passwordHistory.getPassword())) {
            log.error("기존 비밀번호와 불일치");
            throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        User updateUser = findUser.updateUser(requestDto);
        return UserProfileModifyResponseDto.of(updateUser);
    }

    @Transactional
    public void modifyPasswordUser(AuthenticationUser user, UserPasswordModifyRequestDto requestDto) {
        User findUser = userAdaptor.queryUserByEmail(user.getUsername());
        List<PasswordHistory> passwordhistoryList = passwordHistoryAdaptor.queryPasswordHistoryTop4ByUser(findUser);

        for (int i = 0; i < passwordhistoryList.size(); i++) {
            if ((i == 0) && !passwordEncoder.matches(requestDto.password(), passwordhistoryList.get(i).getPassword())) {
                log.error("기존 비밀번호와 불일치");
                throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
            }
            if (passwordEncoder.matches(requestDto.newPassword(), passwordhistoryList.get(i).getPassword())) {
                log.error("최근 사용한 4개의 비밀번호와 일치");
                throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_MATCH);
            }
        }

        PasswordHistory passwordHistory = PasswordHistory.savePasswordHistory(findUser, passwordEncoder.encode(requestDto.newPassword()));
        passwordHistoryAdaptor.savePasswordHistory(passwordHistory);
    }
}
