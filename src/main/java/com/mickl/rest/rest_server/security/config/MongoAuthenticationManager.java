package com.mickl.rest.rest_server.security.config;

import com.mickl.rest.rest_server.security.model.User;
import com.mickl.rest.rest_server.security.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
class MongoAuthenticationManager implements AuthenticationManager {

    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";
    private final UserService userService;

    private final Map<String, PasswordEncoder> encoders;

    @Autowired
    MongoAuthenticationManager(UserService userService, Map<String, PasswordEncoder> encoders) {
        this.userService = userService;
        this.encoders = encoders;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.getByUsername(name)
                .orElseThrow(() -> new BadCredentialsException("User Not Found!"));

        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder(extractId(user.getPassword()),encoders);
        if (encoder.matches(password, user.getPassword())) {
            log.trace("User logged in: ".concat(name));
            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    user.getAuthorities());
        }

        throw new BadCredentialsException("User or password incorrect!");
    }

    private String extractId(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf(PREFIX);
        if(start != 0) {
            throw new IllegalArgumentException("Incorrect Encoder id!");
        }
        int end = prefixEncodedPassword.indexOf(SUFFIX, start);
        if(end < 0) {
            throw new IllegalArgumentException("Incorrect Encoder id!");
        }
        return prefixEncodedPassword.substring(start + 1, end);
    }
}
