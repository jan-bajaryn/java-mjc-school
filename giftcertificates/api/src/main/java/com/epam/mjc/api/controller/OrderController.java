package com.epam.mjc.api.controller;

import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/orders")
public interface OrderController {
    @GetMapping
    ResponseEntity<CollectionModel<OrderDto>> search(Integer pageNumber, Integer pageSize, Long userId);

    @GetMapping("/{id}")
    ResponseEntity<OrderDto> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<OrderDto> create(@RequestBody OrderForCreate orderForCreate);
}
