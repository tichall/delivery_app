package com.sparta.delivery_app.domain.store.adaptor;

import com.sparta.delivery_app.common.globalcustomexception.StoreDuplicatedException;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode.DUPLICATED_STORE;

@Component
@RequiredArgsConstructor
public class StoreAdaptor {

    private final StoreRepository storeRepository;

    public void validByUserIdOrStoreRegistrationNumber(User user, String storeRegistrationNumber ) {
        storeRepository.findByUserOrStoreRegistrationNumber(user, storeRegistrationNumber)
                .ifPresent(s -> {
                            throw new StoreDuplicatedException(DUPLICATED_STORE);
                        }
                );
    }

    //동일한 사업자 번호 / 주소지 확인 -> 저장
    @Transactional
    public Store saveStore(RegisterStoreRequestDto requestDto, User user) {

        Store newStore = new Store(requestDto, user);
        storeRepository.save(newStore);
        return newStore;
    }

}