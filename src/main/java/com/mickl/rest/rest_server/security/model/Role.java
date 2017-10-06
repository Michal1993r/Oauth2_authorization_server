package com.mickl.rest.rest_server.security.model;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;

public class Role implements GrantedAuthority {

    static final long serialVersionUID = 1L;

    @NotNull
    @Setter
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
