package com.sparta.delivery_app.domain.store.repository;

import com.sparta.delivery_app.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
