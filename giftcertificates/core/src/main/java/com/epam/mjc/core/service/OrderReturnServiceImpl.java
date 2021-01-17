package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.CollectionDto;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.service.OrderReturnService;
import com.epam.mjc.api.service.SecurityService;
import com.epam.mjc.api.service.help.OrderService;
import com.epam.mjc.api.service.mapper.OrderMapper;
import com.epam.mjc.api.util.HateoasManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderReturnServiceImpl implements OrderReturnService {

    private static final Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);

    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final HateoasManager hateoasManager;
    private final SecurityService securityService;

    @Autowired
    public OrderReturnServiceImpl(OrderMapper orderMapper, OrderService orderService, HateoasManager hateoasManager, SecurityService securityService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
        this.hateoasManager = hateoasManager;
        this.securityService = securityService;
    }

    @Override
    public CollectionDto<OrderDto> search(Long userId, Integer pageNumber, Integer pageSize) {
        Optional<User> principal = securityService.getPrincipal();

        if (principal.isPresent() && principal.get().getRole() == Role.ADMIN) {
            Page<Order> result = orderService.search(userId, pageNumber, pageSize);
            CollectionDto<OrderDto> model = new CollectionDto<>(result.getTotalPages(),
                    orderMapper.toOrderDto(result.getContent()));
            log.debug("model = {}", model);
            hateoasManager.setCollectionLinksOrderAdmin(model);
            return model;
        } else {
            Page<Order> result = orderService.search(principal.get().getId(), pageNumber, pageSize);
            CollectionDto<OrderDto> model = new CollectionDto<>(result.getTotalPages(),
                    orderMapper.toOrderDto(result.getContent()));
            log.debug("model = {}", model);
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

        Optional<User> principal = securityService.getPrincipal();


        OrderDto orderDto;
        if (principal.isPresent() && principal.get().getRole() == Role.ADMIN) {
            orderDto = orderMapper.toOrderDto(orderService.create(orderForCreate));
            hateoasManager.setSelfLinks(orderDto);
        } else {
            orderForCreate.setUserId(principal.get().getId());
            orderDto = orderMapper.toOrderDto(orderService.create(orderForCreate));
        }
        return orderDto;
    }
}
