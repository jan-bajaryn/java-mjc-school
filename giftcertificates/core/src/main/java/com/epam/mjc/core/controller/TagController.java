package com.epam.mjc.core.controller;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(final TagService tagService) {
        this.tagService = tagService;
    }

    private static final Logger log = LoggerFactory.getLogger(TagController.class);

    @PostMapping("/{name}")
    public ResponseEntity<Tag> tagCreate(@PathVariable String name) {
        log.debug("tagCreate: name = {}", name);
        return new ResponseEntity<>(tagService.createByName(name), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> tagDelete(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.deleteById(id));
    }

    @GetMapping
    public ResponseEntity<List<Tag>> showAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> showById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

}
