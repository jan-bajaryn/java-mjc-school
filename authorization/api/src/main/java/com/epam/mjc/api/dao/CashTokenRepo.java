package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.User;

public interface CashTokenRepo {
    boolean isRefreshTokenExists(String token);
    void setTokens(User user, String refreshToken);
}
