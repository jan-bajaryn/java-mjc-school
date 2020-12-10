package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.CashTokenRepo;
import com.epam.mjc.api.domain.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CashTokenRepoImpl implements CashTokenRepo {

    ConcurrentHashMap<Long, String> tokens = new ConcurrentHashMap<>();

    @Override
    public boolean isRefreshTokenExists(String token) {
        return tokens.containsValue(token);
    }

    @Override
    public void setTokens(User user, String refreshToken) {
        tokens.put(user.getId(), refreshToken);
    }
}
