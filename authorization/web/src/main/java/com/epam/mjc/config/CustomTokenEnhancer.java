package com.epam.mjc.config;

import com.epam.mjc.api.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    private static final Logger log = LoggerFactory.getLogger(CustomTokenEnhancer.class);


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        // TODO check if we need check for null
        if (user.getId() != null) {
            info.put("id", user.getId());
        }
        if (user.getUsername() != null) {
            info.put("username", user.getUsername());
        }

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        OAuth2AccessToken enhance = super.enhance(customAccessToken, authentication);
        System.out.println("enhance.getValue() = " + enhance.getValue());
        System.out.println("enhance.getRefreshToken().getValue() = " + enhance.getRefreshToken().getValue());
        log.debug("enhance.getValue() = {}", enhance.getValue());
        log.debug("enhance.getRefreshToken().getValue() = {}", enhance.getRefreshToken().getValue());
        return enhance;
    }
}
