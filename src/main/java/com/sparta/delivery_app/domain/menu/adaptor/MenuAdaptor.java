package com.sparta.delivery_app.domain.menu.adaptor;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuAdaptor {

    private final MenuRepository menuRepository;

    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }
}
