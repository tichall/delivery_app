package com.sparta.delivery_app.domain.store.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.StoreDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.StoreNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.StoreRegisteredHistoryException;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode.DUPLICATED_STORE_BUSINESS_NUMBER;

@Component
@RequiredArgsConstructor
public class StoreAdaptor {

    private final StoreRepository storeRepository;

    public Store queryStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() ->
                new StoreNotFoundException(StoreErrorCode.INVALID_STORE));
    }

    public void queryStoreHistory(User user) {
        storeRepository.findStoreByUser(user)
                .ifPresent(s -> {
                    throw new StoreRegisteredHistoryException(StoreErrorCode.STORE_REGISTERED_HISTORY);
                });
    }

    public void queryStoreRegistrationNumber(User user, String storeRegistrationNumber) {
        storeRepository.findByUserOrStoreRegistrationNumber(user, storeRegistrationNumber)
                .ifPresent(s -> {
                            throw new StoreDuplicatedException(DUPLICATED_STORE_BUSINESS_NUMBER);
                        }
                );
    }

    public Store queryStoreId(User user) {
        return storeRepository.findStoreByUser(user).orElseThrow(() ->
                new StoreNotFoundException(StoreErrorCode.INVALID_STORE)
        );
    }

    @Transactional
    public Store saveNewStore(final RegisterStoreRequestDto requestDto, User user) {

        Store newStore = new Store(requestDto, user);
        storeRepository.save(newStore);

        return newStore;
    }

    @Transactional
    public void saveModifiedStore(Store store) {
        storeRepository.save(store);
    }

    public Store findById(Long storeId) {
        Optional<Store> store = storeRepository.findById(storeId);

        if (!store.isEmpty()) {
            throw new StoreNotFoundException(StoreErrorCode.INVALID_STORE);
        }

        return store.get();
    }
}