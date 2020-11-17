package com.epam.mjc.api.service;

import com.epam.mjc.api.model.dto.UserDto;

import java.util.List;

public interface UserReturnService {
    List<UserDto> findAll();

    UserDto findById(Long id);
}
