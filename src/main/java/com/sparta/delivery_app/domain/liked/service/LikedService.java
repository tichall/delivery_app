package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.liked.adaptor.LikedAdaptor;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedAdaptor likedAdaptor;
    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;

    public void addLiked(AuthenticationUser user, final Long storeId) {

        Store store = storeAdaptor.queryStoreById(storeId);
        User findUser = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        if (likedAdaptor.existsByStoreAndUser(store, findUser)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_ERROR);
        }

        Liked liked = new Liked(store, findUser);
        likedAdaptor.saveLiked(liked);
    }

    @Transactional
    public void deleteLiked(AuthenticationUser user, final Long storeId) {
        Store store = storeAdaptor.queryStoreById(storeId);
        User findUser = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        if (!likedAdaptor.existsByStoreAndUser(store, findUser)) {
            throw new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR);
        }

        Liked findLiked = likedAdaptor.queryLikedByStoreId(storeId);
        likedAdaptor.deleteLiked(findLiked);
    }
}
