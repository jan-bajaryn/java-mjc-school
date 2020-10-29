package com.epam.mjc.controller;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Boolean> tagCreate(@PathVariable String name) {
        return ResponseEntity.ok(tagService.createByName(name));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> tagDelete(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.deleteById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Tag>> showAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> showById(@PathVariable Long id) {
        Optional<Tag> byId = tagService.findById(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
