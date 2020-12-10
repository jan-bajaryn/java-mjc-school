package com.epam.mjc.api.service;

import com.epam.mjc.api.model.UserForCreate;
import com.epam.mjc.api.model.dto.UserDto;

public interface UserReturnService {
    Object findAll(Integer pageNumber, Integer pageSize);

    UserDto findById(Long id);

    UserDto signUp(UserForCreate userForCreate);
}
