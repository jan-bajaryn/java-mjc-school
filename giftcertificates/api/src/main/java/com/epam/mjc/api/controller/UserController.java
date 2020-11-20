package com.epam.mjc.api.controller;

import com.epam.mjc.api.model.dto.UserDto;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserController {
    @GetMapping
    ResponseEntity<CollectionModel<UserDto>> findAll(Integer pageNumber, Integer pageSize);

    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable Long id);
}
