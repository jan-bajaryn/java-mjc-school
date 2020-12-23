package com.epam.mjc.config;

import com.epam.mjc.core.service.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Value("${config.oauth2.clientid}")
    private String clientid;

    @Value("${config.oauth2.clientSecret}")
    private String clientSecret;


    private final CustomTokenEnhancer converter;
    private final CustomPasswordEncoder customPasswordEncoder;

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;

    @Autowired
    public OAuth2Config(CustomTokenEnhancer converter, CustomPasswordEncoder customPasswordEncoder, AuthenticationManager authenticationManager) {
        this.converter = converter;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientid).secret(customPasswordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(18000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(converter);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(converter);
    }

}
