package com.epam.mjc.api.service;

import com.epam.mjc.api.domain.User;
import org.springframework.transaction.annotation.Transactional;

public interface TokenService {
    boolean isRefreshTokenExists(String token);

    void setTokens(User user, String refreshToken);
}
