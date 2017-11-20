package com.mickl.rest.rest_server.controller;

import com.mickl.rest.rest_server.security.model.User;
import com.mickl.rest.rest_server.security.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public User createNewUser(@RequestBody User user) {
        log.debug(user.toString());
        return userService.createNew(user);
    }
}
