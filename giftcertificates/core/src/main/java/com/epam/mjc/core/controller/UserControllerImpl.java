package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.UserController;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.service.UserReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class UserControllerImpl implements UserController {

    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";

    private final UserReturnService userReturnService;

    @Autowired
    public UserControllerImpl(UserReturnService userReturnService) {
        this.userReturnService = userReturnService;
    }

    @Override
    public ResponseEntity<CollectionModel<UserDto>> findAll(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        List<UserDto> all = userReturnService.findAll(pageNumber, pageSize);

        CollectionModel<UserDto> model = new CollectionModel<>(all);
        model.add(linkTo(UserControllerImpl.class).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @Override
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto byId = userReturnService.findById(id);
        return ResponseEntity.ok(byId);
    }

}
