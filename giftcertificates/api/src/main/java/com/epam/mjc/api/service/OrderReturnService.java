package com.epam.mjc.api.service;

import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;

import java.util.List;

public interface OrderReturnService {
    List<OrderDto> search(Long userId,Integer pageNumber,Integer pageSize);

    OrderDto findById(Long id);

    OrderDto create(OrderForCreate orderForCreate);
}
