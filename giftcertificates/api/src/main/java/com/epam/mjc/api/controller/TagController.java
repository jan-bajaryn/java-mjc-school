package com.epam.mjc.api.controller;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.TagForCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/tag")
public interface TagController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Tag> tagCreate(@RequestBody TagForCreate tagForCreate);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Boolean> tagDelete(@PathVariable Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<Tag>> showAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Tag> showById(@PathVariable Long id);
}
