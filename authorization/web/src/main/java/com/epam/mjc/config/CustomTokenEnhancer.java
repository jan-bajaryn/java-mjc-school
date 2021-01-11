package com.epam.mjc.config;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.TokenService;
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

    private final TokenService tokenService;

    @Autowired
    public CustomTokenEnhancer(TokenService tokenService) {
        this.tokenService = tokenService;
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

        info.put("id", user.getId());
        info.put("role", user.getRole().name());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        OAuth2AccessToken enhance = super.enhance(customAccessToken, authentication);
        tokenService.setTokens(user, enhance.getRefreshToken().getValue());
        return enhance;
    }


    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        if (!tokenService.isRefreshTokenExists(value)) {
            throw new InvalidTokenException("There not so refresh tokens");
        }
        return super.extractAccessToken(value, map);
    }


}
