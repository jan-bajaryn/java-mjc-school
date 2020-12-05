package com.epam.mjc.api.controller;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.TagForCreate;
import com.epam.mjc.api.model.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/tags")
public interface TagController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<TagDto> tagCreate(@RequestBody TagForCreate tagForCreate);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> tagDelete(@PathVariable Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CollectionModel<TagDto>> showAll(Integer pageNumber, Integer pageSize, User principal);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<TagDto> showById(@PathVariable Long id, User principal);
}
