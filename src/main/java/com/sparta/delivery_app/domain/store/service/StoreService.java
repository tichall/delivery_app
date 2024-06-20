package com.sparta.delivery_app.domain.store.service;

import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.response.RegisterStoreResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;

    //회원가입 후 로그인한 유저가 매장 등록 // 추후 필요 조건: 유효하지 않는 JWT 토큰입니다. 403 //이미 매장 삭제한 경우 valid
    public RegisterStoreResponseDto registerStore(RegisterStoreRequestDto requestDto, User user) {
        Long userId = user.getId();
        // 매장 등록권한 - User/Admin/Etc 확인과 매장목록에 해당 userId 있는지 확인/권한 검증
        User checkedManager = userAdaptor.checkManagerRole(userId);
        storeAdaptor.validByUserIdOrStoreRegistrationNumber(checkedManager, requestDto.getStoreRegistrationNumber());
        Store newStore = storeAdaptor.saveStore(requestDto, user);

        RegisterStoreResponseDto responseDto = new RegisterStoreResponseDto(newStore);
        return responseDto;
    }
}



