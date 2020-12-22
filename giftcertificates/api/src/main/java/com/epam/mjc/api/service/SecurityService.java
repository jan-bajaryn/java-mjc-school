package com.epam.mjc.api.service;

import com.epam.mjc.api.domain.User;

import java.util.Optional;

public interface SecurityService {
    Optional<User> getPrincipal();
}
