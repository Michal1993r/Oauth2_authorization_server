package com.mickl.rest.rest_server.security.model;

import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;

public class Role implements GrantedAuthority {

    static final long serialVersionUID = 1L;

    @NotNull
    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
