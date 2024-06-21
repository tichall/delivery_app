package com.sparta.delivery_app.domain.admin.adminuser;

import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {

    private final UserAdaptor userAdaptor;

    public List<AdminUserResponseDto> getAllUserList() {

        List<AdminUserResponseDto> allUserList = userAdaptor.queryAllUser();
        if (allUserList.isEmpty()) {
            throw new EntityNotFoundException("등록된 사용자가 없습니다.");
        }

        return allUserList;
    }
}
