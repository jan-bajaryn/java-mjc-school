package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.OrderController;
import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.service.OrderReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<?> search(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                    @RequestParam(required = false) Long userId,
                                    @AuthenticationPrincipal User principal) {
        if (principal.getRole() == Role.ADMIN) {
            List<OrderDto> search = orderReturnService.search(userId, pageNumber, pageSize);
            for (OrderDto orderDto : search) {
                setSelfLinks(orderDto);
            }
            CollectionModel<OrderDto> model = new CollectionModel<>(search);
            model.add(linkTo(OrderControllerImpl.class).withSelfRel());
            model.add(linkTo(methodOn(OrderControllerImpl.class).create(new OrderForCreate(), null)).withRel("create"));
            return ResponseEntity.ok(model);
        } else {
            List<OrderDto> search = orderReturnService.search(principal.getId(), pageNumber, pageSize);
            CollectionModel<OrderDto> model = new CollectionModel<>(search);
            model.add(linkTo(OrderControllerImpl.class).withSelfRel());
            model.add(linkTo(methodOn(OrderControllerImpl.class).create(new OrderForCreate(), null)).withRel("create"));
            return ResponseEntity.ok(model);
        }
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
        OrderDto byId = orderReturnService.findById(id);
        setSelfLinks(byId);
        return ResponseEntity.ok(byId);
    }

    @Override
    @PostMapping
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<OrderDto> create(@RequestBody OrderForCreate orderForCreate,
                                           @AuthenticationPrincipal User principal) {
        OrderDto orderDto = orderReturnService.create(orderForCreate);
        if (principal.getRole() == Role.ADMIN) {
            setSelfLinks(orderDto);
        }
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    private void setSelfLinks(OrderDto byId) {
        Link selfLink = linkTo(methodOn(OrderController.class)
                .findById(byId.getId())).withSelfRel();
        byId.add(selfLink);
    }

}
