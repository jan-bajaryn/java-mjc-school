package com.epam.mjc.api.controller;

import com.epam.mjc.api.domain.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/tag")
public interface TagController {
    @PostMapping("/{name}")
    ResponseEntity<Tag> tagCreate(@PathVariable String name);

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> tagDelete(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<Tag>> showAll();

    @GetMapping("/{id}")
    ResponseEntity<Tag> showById(@PathVariable Long id);
}
