package com.sparta.delivery_app.domain.store.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.dto.request.ModifyStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.response.ModifyStoreResponseDto;
import com.sparta.delivery_app.domain.store.dto.response.RegisterStoreResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;

    //회원가입 후 로그인한 유저가 매장 등록
    public RegisterStoreResponseDto registerStore(final RegisterStoreRequestDto requestDto, AuthenticationUser authenticationUser) {
        log.info("Service-Register Store");
        String email = authenticationUser.getUsername();
        User user = userAdaptor.queryUserByEmail(email);
        Long userId = user.getId();


        log.info("Service-Check Registration Authority");
        User checkedManager = userAdaptor.checkManagerRole(userId);
        storeAdaptor.queryStoreHistory(checkedManager);
        storeAdaptor.queryStoreRegistrationNumber(checkedManager, requestDto.storeRegistrationNumber());
        Store newStore = storeAdaptor.saveNewStore(requestDto, user);

        return RegisterStoreResponseDto.of(newStore);
    }

    @Transactional
    public ModifyStoreResponseDto modifyStore(final ModifyStoreRequestDto requestDto, AuthenticationUser authenticationUser) {
        log.info("Service-ModifyStore");
        String email = authenticationUser.getUsername();
        User user = userAdaptor.queryUserByEmail(email);
        Long userId = user.getId();

        log.info("Service-ENABLE 상태인 MANAGER 소유의 Store 확인");
        User checkStoreOwner = userAdaptor.checkManagerRole(userId);
        Store ownedStore = storeAdaptor.queryStoreId(checkStoreOwner);
        storeAdaptor.queryStoreStatus(ownedStore);
        ownedStore.modifyStore(requestDto);
        storeAdaptor.saveModifiedStore(ownedStore);

        return ModifyStoreResponseDto.of(ownedStore);
    }
}



