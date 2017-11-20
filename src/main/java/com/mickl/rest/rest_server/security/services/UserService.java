package com.mickl.rest.rest_server.security.services;

import com.mickl.rest.rest_server.security.model.User;
import com.mickl.rest.rest_server.security.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    public Optional<com.mickl.rest.rest_server.security.model.User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createNew(@Valid User user) {
        return userRepository.save(user);
    }
}
