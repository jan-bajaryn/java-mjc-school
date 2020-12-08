package com.epam.mjc.core.service;

import com.epam.mjc.api.dao.UserRepo;
import com.epam.mjc.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public boolean deleteRefreshTokenIfExists(String token) {
        Optional<User> byRefreshToken = userRepo.findByRefreshToken(token);
        if (byRefreshToken.isPresent()) {
            System.out.println("is present");
            User user = byRefreshToken.get();
            user.setRefreshToken(null);
            user.setAccessToken(null);
            userRepo.save(user);
            return true;
        }
        System.out.println("is not present");
        return false;
    }

    @Transactional
    public void setTokens(User user, String accessToken, String refreshToken) {
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        User save = userRepo.save(user);
    }
}
