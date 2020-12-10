package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.service.OrderReturnService;
import com.epam.mjc.api.service.help.OrderService;
import com.epam.mjc.api.service.mapper.OrderMapper;
import com.epam.mjc.api.util.HateoasManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderReturnServiceImpl implements OrderReturnService {

    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final HateoasManager hateoasManager;


    @Autowired
    public OrderReturnServiceImpl(OrderMapper orderMapper, OrderService orderService, HateoasManager hateoasManager) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.hateoasManager = hateoasManager;
    }

    @Override
    public CollectionModel<OrderDto> search(Long userId, Integer pageNumber, Integer pageSize) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getRole() == Role.ADMIN) {
            List<OrderDto> search = orderMapper.toOrderDto(orderService.search(userId, pageNumber, pageSize));
            CollectionModel<OrderDto> model = new CollectionModel<>(search);
            hateoasManager.setCollectionLinksOrderAdmin(model);
            return model;
        } else {
            List<OrderDto> search = orderMapper.toOrderDto(orderService.search(principal.getId(), pageNumber, pageSize));
            CollectionModel<OrderDto> model = new CollectionModel<>(search);
            hateoasManager.setCollectionLinksUser(model);
            return model;
        }
    }

    @Override
    public OrderDto findById(Long id) {
        OrderDto orderDto = orderMapper.toOrderDto(orderService.findById(id));
        hateoasManager.setSelfLinks(orderDto);
        return orderDto;
    }

    @Override
    public OrderDto create(OrderForCreate orderForCreate) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        OrderDto orderDto;
        if (principal.getRole() == Role.ADMIN) {
            orderDto = orderMapper.toOrderDto(orderService.create(orderForCreate));
            hateoasManager.setSelfLinks(orderDto);
        } else {
            orderForCreate.setUserId(principal.getId());
            orderDto = orderMapper.toOrderDto(orderService.create(orderForCreate));
        }
        return orderDto;
    }
}
