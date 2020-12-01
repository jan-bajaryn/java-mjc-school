package com.epam.mjc.core.service.auth;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

//@Component
@PropertySource("classpath:application.properties")
public class CustomPasswordEncoder extends BCryptPasswordEncoder {

    @Value("${password.secret}")
    private String secret;
//    private static final String secret = "alkdjfgirldk";

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword + secret);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return super.matches(rawPassword + secret, encodedPassword);
    }
}
