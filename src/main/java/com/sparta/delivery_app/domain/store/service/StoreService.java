package com.sparta.delivery_app.domain.store.service;

import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.dto.request.ModifySotoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.response.ModifyStoreResponseDto;
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

    //회원가입 후 로그인한 유저가 매장 등록 // 추후 필요 조건: 유효하지 않는 JWT 토큰입니다. 403
    public RegisterStoreResponseDto registerStore(RegisterStoreRequestDto requestDto, User user) {
        Long userId = user.getId();
        // 매장 등록권한 확인
        User checkedManager = userAdaptor.checkManagerRole(userId);
        storeAdaptor.checkStoreHistory(checkedManager);
        storeAdaptor.validStoreRegistrationNumber(checkedManager, requestDto.getStoreRegistrationNumber());
        Store newStore = storeAdaptor.saveStore(requestDto, user);

        return RegisterStoreResponseDto.of(newStore);
    }

    public ModifyStoreResponseDto modifyStore(ModifySotoreRequestDto requestDto, User user) {

        // ENABLE 상태의 MANAGER 소유의 Store 확인하여 수정
        Long userId = user.getId();
        User checkStoreOwner = userAdaptor.checkManagerRole(userId);
        Store ownedStore = storeAdaptor.checkStoreId(checkStoreOwner);
        Store modifiedStore = storeAdaptor.modifyStore(requestDto, ownedStore);

        return ModifyStoreResponseDto.of(modifiedStore);
    }
}



