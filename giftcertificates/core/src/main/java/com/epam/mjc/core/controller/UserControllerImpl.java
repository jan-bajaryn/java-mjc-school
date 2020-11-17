package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.UserController;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.service.UserReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private final UserReturnService userReturnService;

    @Autowired
    public UserControllerImpl(UserReturnService userReturnService) {
        this.userReturnService = userReturnService;
    }

    @Override
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userReturnService.findAll());
    }

    @Override
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userReturnService.findById(id));
    }
}
