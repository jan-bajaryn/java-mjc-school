package com.epam.mjc.api.service.help;

import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.OrderForCreate;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<Order> search(Long userId, Integer pageNumber, Integer pageSize);

    Order findById(Long id);

    Order create(OrderForCreate orderForCreate);
}
