package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.service.UserReturnService;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.api.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReturnServiceImpl implements UserReturnService {

    private static final Logger log = LoggerFactory.getLogger(UserReturnServiceImpl.class);

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserReturnServiceImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll(Integer pageNumber,Integer pageSize) {
        log.debug("findAll: pageSize = {}", pageSize);
        List<User> all = userService.findAll(pageNumber,pageSize);
        log.debug("findAll: all = {}", all);
        List<UserDto> userDtos = userMapper.toUserDto(all);
        log.debug("findAll: userDtos = {}", userDtos);
        return userDtos;
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.toUserDto(userService.findById(id));
    }
}
