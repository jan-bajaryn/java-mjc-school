package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityServiceImpl implements SecurityService {
    @Override
    public Optional<User> getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return Optional.of((User) principal);
        }
        return Optional.empty();
    }
}
