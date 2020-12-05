package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.TagController;
import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.TagForCreate;
import com.epam.mjc.api.model.dto.TagDto;
import com.epam.mjc.api.service.TagReturnService;
import com.epam.mjc.api.util.HateoasManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagControllerImpl implements TagController {


    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";

    private static final Logger log = LoggerFactory.getLogger(TagControllerImpl.class);

    private final TagReturnService tagReturnService;
    private final HateoasManager hateoasManager;

    @Autowired
    public TagControllerImpl(TagReturnService tagReturnService, HateoasManager hateoasManager) {
        this.tagReturnService = tagReturnService;
        this.hateoasManager = hateoasManager;
    }


    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<TagDto> tagCreate(@RequestBody TagForCreate tagForCreate) {
        log.debug("tagCreate: name = {}", tagForCreate.getName());
        TagDto byName = tagReturnService.createByName(tagForCreate.getName());
        hateoasManager.setSelfLinksAdmin(byName);
        return new ResponseEntity<>(
                byName,
                HttpStatus.CREATED
        );
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<Void> tagDelete(@PathVariable Long id) {
        tagReturnService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CollectionModel<TagDto>> showAll(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                           @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                                           @AuthenticationPrincipal User principal) {
        List<TagDto> all = tagReturnService.findAll(pageNumber, pageSize);
        CollectionModel<TagDto> model = new CollectionModel<>(all);

        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.collectionLinksAdmin(model);
        } else {
            hateoasManager.tagCollectionLinksNotAdmin(model);
        }


        return ResponseEntity.ok(
                model
        );


    }

    @Override
    public ResponseEntity<TagDto> showById(@PathVariable Long id,
                                           @AuthenticationPrincipal User principal) {
        TagDto byId = tagReturnService.findById(id);

        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.setSelfLinksAdmin(byId);
        } else {
            hateoasManager.selfLinksNotAdmin(byId);
        }
        return ResponseEntity.ok(
                byId
        );
    }

    @GetMapping("/popular")
    public ResponseEntity<TagDto> getRich(@AuthenticationPrincipal User principal) {
        TagDto result = tagReturnService.findMostPopularTagOfUserHigherCostOrders();

        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.setSelfLinksAdmin(result);
        } else {
            hateoasManager.selfLinksNotAdmin(result);
        }

        return ResponseEntity.ok(
                result
        );
    }

}
