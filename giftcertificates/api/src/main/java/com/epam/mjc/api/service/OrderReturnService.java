package com.epam.mjc.api.service;

import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.CollectionDto;
import com.epam.mjc.api.model.dto.OrderDto;

public interface OrderReturnService {
    CollectionDto<OrderDto> search(Long userId, Integer pageNumber, Integer pageSize);

    OrderDto findById(Long id);

    OrderDto create(OrderForCreate orderForCreate);
}
