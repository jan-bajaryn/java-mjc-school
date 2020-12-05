package com.epam.mjc.api.controller;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/orders")
public interface OrderController {
    @GetMapping
    ResponseEntity<?> search(Integer pageNumber, Integer pageSize, Long userId, User principal);

    @GetMapping("/{id}")
    ResponseEntity<OrderDto> findById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<OrderDto> create(@RequestBody OrderForCreate orderForCreate, User principal);
}
