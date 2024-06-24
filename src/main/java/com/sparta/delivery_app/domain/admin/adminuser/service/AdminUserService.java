package com.sparta.delivery_app.domain.admin.adminuser.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.admin.adminuser.dto.AdminUserResponseDto;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.delivery_app.domain.user.entity.UserStatus.checkManagerEnable;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {

    private final UserAdapter userAdapter;

    public List<AdminUserResponseDto> getAllUserList(
            int page, int size, AuthenticationUser authenticationUser) {

        //ADMIN 권한의 유저 Status 가 ENABLE 인지 확인
        String email = authenticationUser.getUsername();
        User enableUser = userAdapter.queryUserByEmail(email);
        checkManagerEnable(enableUser);

        //페이지 정보 추출
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userAdapter.queryAllUserPage(pageable);
        String pageInfo = userPage.getNumber() + "/" + userPage.getTotalPages();

        //user 정보 추출
        List<AdminUserResponseDto> responseDtoList = userPage.getContent().stream()
                .map(user -> new AdminUserResponseDto(user, pageInfo))
                .collect(Collectors.toList());

        return responseDtoList;
    }
}
