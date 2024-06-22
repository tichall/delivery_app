package com.sparta.delivery_app.domain.admin.adminuser;

import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {

    private final UserAdaptor userAdaptor;

    //admin 검증 필요
    public List<AdminUserResponseDto> getAllUserList(int page, int size) {

        //페이지 정보 추출
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userAdaptor.queryAllUserPage(pageable);
        String pageInfo = userPage.getNumber() + "/" + userPage.getTotalPages();

        //user 정보 추출
        List<AdminUserResponseDto> responseDtoList = userPage.getContent().stream()
                .map(user -> new AdminUserResponseDto(user, pageInfo))
                .collect(Collectors.toList());

        return responseDtoList;
    }
}
