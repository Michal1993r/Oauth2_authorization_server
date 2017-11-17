package com.mickl.rest.rest_server.security.services;

import com.mickl.rest.rest_server.security.repositories.ClientRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service("clientService")
public class ClientService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException{
        return clientRepository.findByClientId(clientId).orElseThrow(() -> new ClientRegistrationException("Client not found!"));
    }
}
