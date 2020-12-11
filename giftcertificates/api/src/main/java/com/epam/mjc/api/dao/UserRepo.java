package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

}
