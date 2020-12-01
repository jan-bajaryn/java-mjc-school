package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {


    List<Order> findAllByUserId(Long userId, Pageable pageable);
}
