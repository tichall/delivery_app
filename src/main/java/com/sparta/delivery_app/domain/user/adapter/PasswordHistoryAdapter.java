package com.sparta.delivery_app.domain.user.adapter;

import com.sparta.delivery_app.common.exception.errorcode.CommonErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalServerException;
import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.repository.PasswordHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PasswordHistoryAdapter {
    private final PasswordHistoryRepository passwordHistoryRepository;

    public PasswordHistory queryPasswordHistoryTop1ByUser(User user) {
        return passwordHistoryRepository.findTop1ByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() -> new GlobalServerException(CommonErrorCode.INTERNAL_SERVER_ERROR)
                );
    }

    public List<PasswordHistory> queryPasswordHistoryTop4ByUser(User user) {
        return passwordHistoryRepository.findTop4ByUserOrderByCreatedAtDesc(user);
    }

    public void savePasswordHistory(PasswordHistory passwordHistory) {
        passwordHistoryRepository.save(passwordHistory);
    }
}
