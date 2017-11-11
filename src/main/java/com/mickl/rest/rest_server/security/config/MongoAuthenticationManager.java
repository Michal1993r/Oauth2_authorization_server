package com.mickl.rest.rest_server.security.config;

import com.mickl.rest.rest_server.security.model.User;
import com.mickl.rest.rest_server.security.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MongoAuthenticationManager implements AuthenticationManager {

    private UserService userService;

    public MongoAuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.getByUsername(name)
                .orElseThrow(() -> new BadCredentialsException("User Not Found!"));

        if (passwordsMatch(password, user)) {
            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    user.getAuthorities());
        }

        throw new BadCredentialsException("Password incorrect!");
    }

    private boolean passwordsMatch(String password, User user) {
        return user.getPassword().equals(password);
    }
}
