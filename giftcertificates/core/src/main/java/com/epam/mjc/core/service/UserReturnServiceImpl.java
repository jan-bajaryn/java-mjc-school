package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.UserForCreate;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.service.UserReturnService;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.api.service.mapper.UserMapper;
import com.epam.mjc.api.util.HateoasManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReturnServiceImpl implements UserReturnService {

    private static final Logger log = LoggerFactory.getLogger(UserReturnServiceImpl.class);

    private final UserService userService;
    private final UserMapper userMapper;
    private final HateoasManager hateoasManager;


    @Autowired
    public UserReturnServiceImpl(UserService userService, UserMapper userMapper, HateoasManager hateoasManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.hateoasManager = hateoasManager;
    }

    @Override
    public Object findAll(Integer pageNumber, Integer pageSize) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getRole() == Role.ADMIN) {
            List<User> all = userService.findAll(pageNumber, pageSize);
            List<UserDto> userDtos = userMapper.toUserDto(all);
            CollectionModel<UserDto> model = new CollectionModel<>(userDtos);
            hateoasManager.setCollectionLinksUserAdmin(model);
            return model;
        } else {
            UserDto byId = userMapper.toUserDto(userService.findById(principal.getId()));
            hateoasManager.setSelfLinksUser(byId);
            return byId;
        }
    }

    @Override
    public UserDto findById(Long id) {
        UserDto userDto = userMapper.toUserDto(userService.findById(id));
        hateoasManager.setSelfLinksAdmin(userDto);
        return userDto;
    }

    @Override
    public UserDto signUp(UserForCreate userForCreate) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDto userDto = userMapper.toUserDto(userService.signUp(userMapper.toUser(userForCreate)));

        if (principal != null) {
            if (principal.getRole() == Role.ADMIN) {
                hateoasManager.setSelfLinksAdmin(userDto);
            } else {
                hateoasManager.setSelfLinksUser(userDto);
            }
        }
        return userDto;
    }
}
