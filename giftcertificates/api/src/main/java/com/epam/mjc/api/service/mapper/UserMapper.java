package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);
}
