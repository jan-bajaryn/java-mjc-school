package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {


    Page<Order> findAllByUserId(Long userId, Pageable pageable);
}
