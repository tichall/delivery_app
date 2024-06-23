package com.sparta.delivery_app;

import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDB {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        @Autowired
        PasswordEncoder passwordEncoder;

        public void dbInit1() {

            save(User.builder()
                    .email("admin@gmail.com")
                    .userAddress("서울시")
                    .nickName("어디민닉네임")
                    .name("관리자")
                    .userRole(UserRole.ADMIN)
                    .userStatus(UserStatus.ENABLE)
                    .password(passwordEncoder.encode("adminpassword"))
                    .build());
        }

        public void save(Object... objects) {
            for (Object object : objects) {
                em.persist(object);
            }
        }
    }
}

