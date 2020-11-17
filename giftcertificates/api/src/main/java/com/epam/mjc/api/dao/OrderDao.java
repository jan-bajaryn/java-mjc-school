package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> search(Long userId);

    Optional<Order> findById(Long id);

    Order create(Order order);
}
