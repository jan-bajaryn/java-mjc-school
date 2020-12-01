package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.TagController;
import com.epam.mjc.api.model.TagForCreate;
import com.epam.mjc.api.model.dto.TagDto;
import com.epam.mjc.api.service.TagReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TagControllerImpl implements TagController {


    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";

    private static final Logger log = LoggerFactory.getLogger(TagControllerImpl.class);

    private final TagReturnService tagReturnService;

    @Autowired
    public TagControllerImpl(TagReturnService tagReturnService) {
        this.tagReturnService = tagReturnService;
    }


    @Override
    public ResponseEntity<TagDto> tagCreate(@RequestBody TagForCreate tagForCreate) {
        log.debug("tagCreate: name = {}", tagForCreate.getName());
        TagDto byName = tagReturnService.createByName(tagForCreate.getName());
        return new ResponseEntity<>(
                byName,
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<Void> tagDelete(@PathVariable Long id) {
        tagReturnService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CollectionModel<TagDto>> showAll(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                           @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        List<TagDto> all = tagReturnService.findAll(pageNumber, pageSize);

        CollectionModel<TagDto> model = new CollectionModel<>(all);

        Link self = linkTo(TagControllerImpl.class).withRel("tags");
        Link rich = linkTo(methodOn(TagControllerImpl.class)
                .getRich()).withRel("popular");
        Link create = linkTo(methodOn(TagControllerImpl.class)
                .tagCreate(new TagForCreate())).withRel("create");
        model.add(self, rich, create);

        return ResponseEntity.ok(
                model
        );


    }

    @Override
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<TagDto> showById(@PathVariable Long id) {
        TagDto byId = tagReturnService.findById(id);
//        setSelfLinks(byId);
        return ResponseEntity.ok(
                byId
        );
    }

    @GetMapping("/popular")
    public ResponseEntity<TagDto> getRich() {
        TagDto result = tagReturnService.findMostPopularTagOfUserHigherCostOrders();
        return ResponseEntity.ok(
                result
        );
    }

}
