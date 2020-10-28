package com.epam.controller;

import com.epam.entity.Tag;
import com.epam.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PutMapping("/tag/create/{name}")
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
