package com.epam.mjc.core.config.auth;

import com.epam.mjc.core.service.auth.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomUserConverter extends DefaultUserAuthenticationConverter {

    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public CustomUserConverter(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostConstruct
    public void init(){
        setUserDetailsService(jwtUserDetailsService);
    }

//    @Override
//    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
//        System.out.println("convertUserAuthentication");
//        return super.convertUserAuthentication(authentication);
//    }

//    @Override
//    public Authentication extractAuthentication(Map<String, ?> map) {
//        System.out.println("workss");
//        if (map.containsKey(USERNAME)) {
//            Object principal = map.get(USERNAME);
//            UserDetails user = userDetailsService.loadUserByUsername((String) map.get(USERNAME));
//            principal = user;
//            return new UsernamePasswordAuthenticationToken(principal, "N/A", getAuthorities(map));
//        }
//        return null;
//    }


//    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
//        if (!map.containsKey(AUTHORITIES)) {
//            return super.getde;
//        }
//        Object authorities = map.get(AUTHORITIES);
//        if (authorities instanceof String) {
//            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
//        }
//        if (authorities instanceof Collection) {
//            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
//                    .collectionToCommaDelimitedString((Collection<?>) authorities));
//        }
//        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
//    }

}
