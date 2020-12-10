package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
