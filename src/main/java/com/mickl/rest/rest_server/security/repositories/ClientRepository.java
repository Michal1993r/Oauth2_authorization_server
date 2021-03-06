package com.mickl.rest.rest_server.security.repositories;

import com.mickl.rest.rest_server.security.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "/clients", collectionResourceRel = "clients")
public interface ClientRepository extends MongoRepository<Client, String>{
    Optional<Client> findByClientId(String clientid);
}
