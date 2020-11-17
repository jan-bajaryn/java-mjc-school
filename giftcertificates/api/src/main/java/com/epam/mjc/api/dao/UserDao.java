package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll(Integer begin, Integer pageSize);

    Optional<User> findById(Long id);
}
