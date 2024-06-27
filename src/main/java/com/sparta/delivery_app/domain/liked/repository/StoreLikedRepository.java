package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreLikedRepository extends JpaRepository<StoreLiked, Long> {
    boolean existsByStoreAndUser(Store store, User user);

    Optional<StoreLiked> findByStoreId(Long storeId);
}
