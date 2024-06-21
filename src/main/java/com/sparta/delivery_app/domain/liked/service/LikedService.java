package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.domain.liked.adaptor.LikedAdaptor;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedAdaptor likedAdaptor;
    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;

    public void addLiked(Long storeId) {

        Store store = storeAdaptor.findById(storeId);

        //TODO user 검증 필요(security)
        User user = userAdaptor.getCurrentUser();

        if (likedAdaptor.existsByStoreAndUser(store, user)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_ERROR);
        }

        Liked liked = new Liked(store, user);
        likedAdaptor.saveLiked(liked);
    }

    public void deleteLiked(Long storeId) {

        Store store = storeAdaptor.findById(storeId);

        //TODO user 검증 필요(security)
        User user = userAdaptor.getCurrentUser();

        if (!likedAdaptor.existsByStoreAndUser(store, user)) {
            throw new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR);
        }

        Liked liked = new Liked(store, user);
        likedAdaptor.deleteLiked(liked);
    }
}
