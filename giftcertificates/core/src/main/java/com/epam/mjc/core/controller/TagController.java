package com.epam.mjc.core.controller;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

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
        Optional<Tag> byId = tagService.findById(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
