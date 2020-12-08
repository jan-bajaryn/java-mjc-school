package com.epam.mjc.core.config.auth;

import com.epam.mjc.api.service.help.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

    private final CustomUserConverter customUserConverter;
    private final UserService userService;


    @Autowired
    public JwtConverter(CustomUserConverter customUserConverter, UserService userService) {
        this.customUserConverter = customUserConverter;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        setUserTokenConverter(this.customUserConverter);
    }

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        converter.setAccessTokenConverter(this);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication auth = super.extractAuthentication(map);

//        AccessTokenMapper details = new AccessTokenMapper();
//        if (map.get("id") != null)
//            details.setId((String) map.get("id"));
//        if (map.get("userName") != null)
//            details.setUserName((String) map.get("userName"));
//        if (map.get("name") != null)
//            details.setName((String) map.get("name"));

        auth.setDetails(auth.getPrincipal());
        return auth;
    }

//    @Override
//    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//        return super.convertAccessToken(token, authentication);
//    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        boolean isExists = userService.isTokenExists(value);
        if (!isExists) {
            throw new InvalidTokenException("There not so access tokens");
        }
        // TODO way to deny token and check in database validation
        return super.extractAccessToken(value, map);
    }
}
