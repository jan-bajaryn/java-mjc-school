package com.epam.mjc.core.config.auth;

import com.epam.mjc.core.service.auth.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CustomUserConverter extends DefaultUserAuthenticationConverter {


    private static final Logger log = LoggerFactory.getLogger(CustomUserConverter.class);
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public CustomUserConverter(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostConstruct
    public void init(){
        setUserDetailsService(jwtUserDetailsService);
    }

    @Override
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        log.info("setDefaultAuthorities called");
        super.setDefaultAuthorities(defaultAuthorities);
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        log.info("convertUserAuthentication called");
        return super.convertUserAuthentication(authentication);
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        log.info("extractAuthentication called");
        return super.extractAuthentication(map);
    }
}
