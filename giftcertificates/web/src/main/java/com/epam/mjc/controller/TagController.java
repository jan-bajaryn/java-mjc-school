package com.epam.mjc.controller;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/tag/create/{name}")
    public ResponseEntity<Boolean> tagCreate(@PathVariable String name) {
        return ResponseEntity.ok(tagService.createByName(name));
    }

    @DeleteMapping("/tag/delete/{id}")
    public ResponseEntity<Boolean> tagDelete(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.deleteById(id));
    }

    @GetMapping("/tag/show-all")
    public ResponseEntity<List<Tag>> showAll() {
        return ResponseEntity.ok(tagService.findAll());
    }
}
