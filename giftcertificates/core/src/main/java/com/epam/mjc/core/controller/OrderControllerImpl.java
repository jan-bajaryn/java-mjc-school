package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.OrderController;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.service.OrderReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderControllerImpl implements OrderController {

    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";


    private final OrderReturnService orderReturnService;

    @Autowired
    public OrderControllerImpl(OrderReturnService orderReturnService) {
        this.orderReturnService = orderReturnService;
    }

    @Override
    @GetMapping
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<?> search(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                    @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(orderReturnService.search(userId,pageNumber,pageSize));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto byId = orderReturnService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @Override
    @PostMapping
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<OrderDto> create(@RequestBody OrderForCreate orderForCreate) {

        return new ResponseEntity<>(orderReturnService.create(orderForCreate), HttpStatus.CREATED);
    }

}
