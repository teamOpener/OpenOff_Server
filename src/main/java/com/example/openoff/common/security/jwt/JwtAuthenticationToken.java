package com.example.openoff.common.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String uuid;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String uuid) {
        super(authorities);
        this.uuid = uuid;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return uuid;
    }
}
