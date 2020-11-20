package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.OrderController;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.service.OrderReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
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
    public ResponseEntity<CollectionModel<OrderDto>> search(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                                            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                                            @RequestParam(required = false) Long userId) {
        List<OrderDto> search = orderReturnService.search(userId, pageNumber, pageSize);

        CollectionModel<OrderDto> model = new CollectionModel<>(search);

        model.add(linkTo(OrderControllerImpl.class).withSelfRel());
        model.add(linkTo(methodOn(OrderControllerImpl.class).create(new OrderForCreate())).withRel("create"));

        return ResponseEntity.ok(model);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto byId = orderReturnService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @Override
    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderForCreate orderForCreate) {
        OrderDto orderDto = orderReturnService.create(orderForCreate);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

}
