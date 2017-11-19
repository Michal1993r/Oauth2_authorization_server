package com.mickl.rest.rest_server.security.repositories;

import com.mickl.rest.rest_server.RestServerApplication;
import com.mickl.rest.rest_server.security.config.MongoConfig;
import com.mickl.rest.rest_server.security.model.Client;
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
@DataMongoTest
@ContextConfiguration(classes = {MongoConfig.class, ClientRepository.class, RestServerApplication.class})
public class ClientRepositoryIT {

    @Autowired
    ClientRepository clientRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByClientId() throws Exception {
        Optional<Client> client = clientRepository.findByClientId("angular");

        assertEquals(true, client.isPresent());

        client.ifPresent(c -> assertEquals("angular", c.getClientId()));
    }

}