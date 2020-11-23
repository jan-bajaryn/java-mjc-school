package com.epam.mjc.core.service;

import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.service.OrderReturnService;
import com.epam.mjc.api.service.mapper.OrderMapper;
import com.epam.mjc.api.service.help.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderReturnServiceImpl implements OrderReturnService {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @Autowired
    public OrderReturnServiceImpl(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @Override
    public List<OrderDto> search(Long userId, Integer pageNumber, Integer pageSize) {
        return orderMapper.toOrderDto(orderService.search(userId, pageNumber, pageSize));
    }

    @Override
    public OrderDto findById(Long id) {
        return orderMapper.toOrderDto(orderService.findById(id));
    }

    @Override
    public OrderDto create(OrderForCreate orderForCreate) {
        return orderMapper.toOrderDto(orderService.create(orderForCreate));
    }
}
