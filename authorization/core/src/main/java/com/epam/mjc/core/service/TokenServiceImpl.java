package com.epam.mjc.core.service;

import com.epam.mjc.api.dao.CashTokenRepo;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final CashTokenRepo cashTokenRepo;

    @Autowired
    public TokenServiceImpl(CashTokenRepo cashTokenRepo) {
        this.cashTokenRepo = cashTokenRepo;
    }

    @Override
    public boolean isRefreshTokenExists(String token) {
        return cashTokenRepo.isRefreshTokenExists(token);
    }

    @Override
    public void setTokens(User user, String refreshToken) {
        cashTokenRepo.setTokens(user,refreshToken);
    }
}
