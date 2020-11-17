package com.epam.mjc.api.service.help;

import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.OrderForCreate;

import java.util.List;

public interface OrderService {
    List<Order> search(Long userId,Integer pageNumber,Integer pageSize);

    Order findById(Long id);

    Order create(OrderForCreate orderForCreate);
}
