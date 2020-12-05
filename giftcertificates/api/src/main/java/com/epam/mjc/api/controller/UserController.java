package com.epam.mjc.api.controller;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.dto.UserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserController {
    @GetMapping
    ResponseEntity<?> findAll(Integer pageNumber, Integer pageSize, User principal);

    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable Long id);
}
