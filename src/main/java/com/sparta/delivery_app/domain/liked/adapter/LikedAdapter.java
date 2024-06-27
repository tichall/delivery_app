package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.StoreLikedRepository;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikedAdapter {

    private final StoreLikedRepository storeLikedRepository;

    public void saveLiked(StoreLiked storeLiked) {
        storeLikedRepository.save(storeLiked);
    }

    public void deleteLiked(StoreLiked storeLiked) {
        storeLikedRepository.delete(storeLiked);
    }

    public StoreLiked queryLikedByStoreId(Long storeId) {
        return storeLikedRepository.findByStoreId(storeId).orElseThrow(() ->
                new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR));
    }

    public boolean existsByStoreAndUser(Store store, User user) {
        return storeLikedRepository.existsByStoreAndUser(store, user);
    }
}
