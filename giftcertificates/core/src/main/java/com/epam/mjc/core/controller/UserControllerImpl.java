package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.UserController;
import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.UserForCreate;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.service.UserReturnService;
import com.epam.mjc.api.util.HateoasManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";

    private final UserReturnService userReturnService;
    private final HateoasManager hateoasManager;

    @Autowired
    public UserControllerImpl(UserReturnService userReturnService, HateoasManager hateoasManager) {
        this.userReturnService = userReturnService;
        this.hateoasManager = hateoasManager;
    }


    @Override
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                     @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                     @AuthenticationPrincipal User principal) {
        if (principal.getRole() == Role.ADMIN) {
            List<UserDto> all = userReturnService.findAll(pageNumber, pageSize);
            CollectionModel<UserDto> model = new CollectionModel<>(all);
            hateoasManager.setCollectionLinksAdmin(model);
            return ResponseEntity.ok(model);
        } else {
            UserDto byId = userReturnService.findById(principal.getId());
            hateoasManager.setSelfLinksUser(byId);
            return ResponseEntity.ok(byId);
        }
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto byId = userReturnService.findById(id);
        hateoasManager.setSelfLinksAdmin(byId);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<UserDto> signUp(
            @RequestBody UserForCreate userForCreate,
            @AuthenticationPrincipal User principal
    ) {
        UserDto created = userReturnService.signUp(userForCreate);
        if (principal != null) {
            if (principal.getRole() == Role.ADMIN) {
                hateoasManager.setSelfLinksAdmin(created);
            } else {
                hateoasManager.setSelfLinksUser(created);
            }
        }
        return ResponseEntity.ok(created);
    }
}
