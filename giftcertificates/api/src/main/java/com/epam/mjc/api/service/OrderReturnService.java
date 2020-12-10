package com.epam.mjc.api.service;

import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.model.dto.OrderDto;
import org.springframework.hateoas.CollectionModel;

public interface OrderReturnService {
    CollectionModel<OrderDto> search(Long userId, Integer pageNumber, Integer pageSize);

    OrderDto findById(Long id);

    OrderDto create(OrderForCreate orderForCreate);
}
