package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.TagController;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.TagForCreate;
import com.epam.mjc.api.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagControllerImpl implements TagController {
    private final TagService tagService;

    public TagControllerImpl(final TagService tagService) {
        this.tagService = tagService;
    }

    private static final Logger log = LoggerFactory.getLogger(TagControllerImpl.class);

    @Override
    public ResponseEntity<Tag> tagCreate(@RequestBody TagForCreate tagForCreate) {
        log.debug("tagCreate: name = {}", tagForCreate.getName());
        return new ResponseEntity<>(tagService.createByName(tagForCreate.getName()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Boolean> tagDelete(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.deleteById(id));
    }

    @Override
    public ResponseEntity<List<Tag>> showAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @Override
    public ResponseEntity<Tag> showById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

}
