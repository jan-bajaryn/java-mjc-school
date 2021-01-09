package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.UserController;
import com.epam.mjc.api.model.UserForCreate;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.service.UserReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserControllerImpl implements UserController {

    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";

    private final UserReturnService userReturnService;

    @Autowired
    public UserControllerImpl(UserReturnService userReturnService) {
        this.userReturnService = userReturnService;
    }


    @Override
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                     @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {

        return ResponseEntity.ok(userReturnService.findAll(pageNumber, pageSize));
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto byId = userReturnService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<UserDto> signUp(
            @RequestBody UserForCreate userForCreate
    ) {
        UserDto created = userReturnService.signUp(userForCreate);
        return ResponseEntity.ok(created);
    }
}
