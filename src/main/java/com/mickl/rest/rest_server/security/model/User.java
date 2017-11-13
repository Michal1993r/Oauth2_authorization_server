package com.mickl.rest.rest_server.security.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Document(collection = "Users")
public class User implements UserDetails {

    static final long serialVersionUID = 1L;

    @Id
    @Getter
    private String id;

    @NotNull
    @Setter
    private String username;

    @NotNull
    private String password;

    @Setter
    private boolean enabled;

    @Setter
    private Set<Role> authorities;

    @Getter
    @Setter
    private String avatar;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setPassword(String password) {
        this.password = "{bcrypt}".concat(new BCryptPasswordEncoder().encode(password));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return username;
    }

}
