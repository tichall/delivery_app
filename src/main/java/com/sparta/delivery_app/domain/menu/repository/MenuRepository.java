package com.sparta.delivery_app.domain.menu.repository;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findAllMenuByStoreId(Long storeId, Pageable pageable);
}