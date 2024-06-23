package com.sparta.delivery_app.domain.user.repository;

import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    Optional<PasswordHistory> findTop1ByUserOrderByCreatedAtDesc(User user);

    List<PasswordHistory> findTop4ByUserOrderByCreatedAtDesc(User user);
}
