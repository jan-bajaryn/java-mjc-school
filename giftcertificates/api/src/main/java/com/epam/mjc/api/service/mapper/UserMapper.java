package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.controller.UserController;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);


    @AfterMapping
    default void afterToUserDto(User from, @MappingTarget UserDto to) {
        setSelfLinks(to);
    }

    default void setSelfLinks(UserDto byId) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .findById(byId.getId())).withSelfRel();
        byId.add(selfLink);
    }

    List<UserDto> toUserDto(List<User> users);
}
