package com.mickl.rest.rest_server.security.repositories;

import com.mickl.rest.rest_server.RestServerApplication;
import com.mickl.rest.rest_server.security.config.MongoConfig;
import com.mickl.rest.rest_server.security.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoConfig.class, RestServerApplication.class})
@DataMongoTest
public class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUsername() throws Exception {

        Optional<User> user = userRepository.findByUsername("mickl");

        assertEquals(true, user.isPresent());

        user.ifPresent(user1 -> assertEquals("mickl", user1.getName()));
    }

}