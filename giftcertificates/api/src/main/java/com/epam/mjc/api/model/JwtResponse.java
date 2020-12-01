package com.epam.mjc.api.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private final String token;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JwtResponse that = (JwtResponse) o;

        if (getToken() != null ? !getToken().equals(that.getToken()) : that.getToken() != null) return false;
        return getAuthorities() != null ? getAuthorities().equals(that.getAuthorities()) : that.getAuthorities() == null;
    }

    @Override
    public int hashCode() {
        int result = getToken() != null ? getToken().hashCode() : 0;
        result = 31 * result + (getAuthorities() != null ? getAuthorities().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
