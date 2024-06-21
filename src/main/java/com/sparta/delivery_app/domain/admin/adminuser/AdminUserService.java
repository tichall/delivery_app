package com.sparta.delivery_app.domain.admin.adminuser;

import com.sparta.delivery_app.common.globalcustomexception.UserNotExistException;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sparta.delivery_app.common.exception.errorcode.UserErrorCode.NOT_FOUND_USER;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {

    private final UserAdaptor userAdaptor;

    public Page<AdminUserResponseDto> getAllUserList(int page, int size) {

//        if (!isAdmin) {
//            throw new AccessDeniedException("관리자 권한이 필요합니다.");
//        }
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminUserResponseDto> allUserList = userAdaptor.queryAllUser(pageable);

        if (allUserList.getTotalElements() == 0) {
            throw new UserNotExistException(NOT_FOUND_USER);
        }


        return allUserList;
    }
}
