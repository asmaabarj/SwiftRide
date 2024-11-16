package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.ClientDTO;
import com.example.SwiftRide.exceptions.AlreadyExistsException;
import com.example.SwiftRide.exceptions.DoesNotExistsException;
import com.example.SwiftRide.models.Client;
import com.example.SwiftRide.repositories.ClientRepository;
import com.example.SwiftRide.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        if (clientRepository.existsById(clientDTO.getId())) {
            throw new AlreadyExistsException("Client with id " + clientDTO.getId() + " already exists");
        }else {
            Client client = modelMapper.map(clientDTO, Client.class);
            client = clientRepository.save(client);
            return modelMapper.map(client, ClientDTO.class);
        }
    }

    @Override
    public Optional<ClientDTO> getClient(Long id) {
        if (clientRepository.existsById(id)) {
            Client client = clientRepository.findById(id).get();
            return Optional.of(modelMapper.map(client, ClientDTO.class));
        }else {
            return Optional.empty();
        }
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        if (clientRepository.existsById(clientDTO.getId())) {
            Client client = modelMapper.map(clientDTO, Client.class);
            client = clientRepository.save(client);
            return modelMapper.map(client, ClientDTO.class);
        }else {
            throw new DoesNotExistsException("Client with id " + clientDTO.getId() + " does not exist");
        }
    }

    @Override
    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        }else {
            throw new DoesNotExistsException("Client with id " + id + " does not exist");
        }
    }

    @Override
    public List<ClientDTO> getAllClient() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            return Collections.emptyList();
        }else {
            return clients.stream()
                    .map(client -> modelMapper.map(client, ClientDTO.class))
                    .collect(Collectors.toList());
        }
    }
}
