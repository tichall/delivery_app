package com.sparta.delivery_app.domain.menu.repository;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStoreId(Long storeId);
}