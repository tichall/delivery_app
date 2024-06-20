package com.sparta.delivery_app.domain.liked.adaptor;

import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.liked.repository.LikedRepository;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikedAdaptor {

    private final LikedRepository likedRepository;

    public void saveLiked(Liked liked) {
        likedRepository.save(liked);
    }

    public void deleteLiked(Liked liked) {
        likedRepository.delete(liked);
    }

    public Optional<Liked> findById(Long id) {
        return likedRepository.findById(id);
    }

    public boolean existsByStoreAndUser(Store store, User user) {
        return likedRepository.existsByStoreAndUser(store, user);
    }
}
