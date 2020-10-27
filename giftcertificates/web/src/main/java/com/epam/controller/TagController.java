package com.epam.controller;

import com.epam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.epam.entity.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PutMapping("/tag/create/{name}")
    public ResponseEntity<Boolean> tagCreate(@PathVariable String name) {
        return null;
    }

    @DeleteMapping("/tag/delete/{id}")
    public ResponseEntity<Boolean> tagDelete(@PathVariable Integer id) {
        return null;
    }

    @GetMapping("/tag/show-all")
    public ResponseEntity<List<Tag>> showAll() {
        return ResponseEntity.ok(tagService.findAll());
    }
}
