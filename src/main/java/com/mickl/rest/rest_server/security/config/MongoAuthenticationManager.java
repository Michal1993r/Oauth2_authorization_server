package com.mickl.rest.rest_server.security.config;

import com.mickl.rest.rest_server.security.model.User;
import com.mickl.rest.rest_server.security.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
class MongoAuthenticationManager implements AuthenticationManager {

    private final UserService userService;

    MongoAuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.getByUsername(name)
                .orElseThrow(() -> new BadCredentialsException("User Not Found!"));

        if (passwordsMatch(password, user)) {
            log.trace("User logged in: ".concat(name));
            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    user.getAuthorities());
        }

        throw new BadCredentialsException("User or password incorrect!");
    }

    private boolean passwordsMatch(String password, User user) {
        return user.getPassword().equals(password);
    }
}
