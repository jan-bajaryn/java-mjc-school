package com.epam.mjc.api.service.help;

import com.epam.mjc.api.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);
}
