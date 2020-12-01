package com.epam.mjc.core.service.auth;

import com.epam.mjc.api.dao.UserRepo;
import com.epam.mjc.api.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public JwtUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepo.findByUsername(username);
        if (byUsername != null) {
            return byUsername;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}