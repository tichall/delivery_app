package com.sparta.delivery_app.domain.store.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.StoreNotFoundException;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StoreAdaptor {

    private final StoreRepository storeRepository;

    public Store queryStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() ->
                new StoreNotFoundException(StoreErrorCode.INVALID_STORE));
    }

    public Store findById(Long storeId) {
        Optional<Store> store = storeRepository.findById(storeId);

        if (!store.isEmpty()) {
            throw new StoreNotFoundException(StoreErrorCode.INVALID_STORE);
        }

        return store.get();
    }
}