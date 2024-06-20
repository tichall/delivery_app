package com.sparta.delivery_app.domain.menu.repository;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
