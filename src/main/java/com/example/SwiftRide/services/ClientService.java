package com.example.SwiftRide.services;

import com.example.SwiftRide.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientDTO saveClient(ClientDTO clientDTO);
    Optional<ClientDTO> getClient(Long id);
    ClientDTO updateClient(ClientDTO clientDTO);
    void deleteClient(Long id);
    List<ClientDTO> getAllClient();
}
