package com.epam.mjc.config;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    private static final Logger log = LoggerFactory.getLogger(CustomTokenEnhancer.class);

    @Value("${config.oauth2.privateKey}")
    private String privateKey;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    private final UserService userService;

    @Autowired
    public CustomTokenEnhancer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        this.setSigningKey(privateKey);
        this.setVerifierKey(publicKey);
    }

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
        userService.setTokens(user, enhance.getValue(), enhance.getRefreshToken().getValue());
        return enhance;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        if (!userService.deleteRefreshTokenIfExists(value)) {
            // TODO replace with other exception
            throw new InvalidTokenException("There not so refresh tokens");
        }
        return super.extractAccessToken(value, map);
    }


}
