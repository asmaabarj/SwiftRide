package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.ClientDTO;
import com.example.SwiftRide.models.Client;
import com.example.SwiftRide.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientDTO clientDTO;

    @BeforeEach
    public void init() {
        clientDTO = ClientDTO.builder()
                .id(1L)
                .fname("John")
                .lname("Doe")
                .email("John@gmail.com")
                .idc("AD12345")
                .phoneNumber("1234567890")
                .build();

        client = Client.builder()
                .id(1L)
                .fname("John")
                .lname("Doe")
                .email("John@gmail.com")
                .idc("AD12345")
                .phoneNumber("1234567890")
                .build();
    }

    @Test
    @DisplayName("test save client")
    void testSaveClient() {
        given(clientRepository.existsById(clientDTO.getId())).willReturn(false);
        given(modelMapper.map(clientDTO, Client.class)).willReturn(client);
        given(clientRepository.save(client)).willReturn(client);
        given(modelMapper.map(client, ClientDTO.class)).willReturn(clientDTO);

        ClientDTO savedClient = clientService.saveClient(clientDTO);

        assertEquals(clientDTO, savedClient);
    }

    @Test
    @DisplayName("test update client")
    void testUpdateClient() {
        given(clientRepository.existsById(clientDTO.getId())).willReturn(true);
        given(modelMapper.map(clientDTO, Client.class)).willReturn(client);
        given(clientRepository.save(client)).willReturn(client);
        given(modelMapper.map(client, ClientDTO.class)).willReturn(clientDTO);

        ClientDTO updatedClient = clientService.updateClient(clientDTO);

        assertEquals(clientDTO, updatedClient);
    }

    @Test
    @DisplayName("test get client")
    void testGetClient() {
        given(clientRepository.existsById(clientDTO.getId())).willReturn(true);
        given(clientRepository.findById(clientDTO.getId())).willReturn(java.util.Optional.of(client));
        given(modelMapper.map(client, ClientDTO.class)).willReturn(clientDTO);

        ClientDTO result = clientService.getClient(1L).get();

        assertEquals(clientDTO, result);
    }

    @Test
    @DisplayName("test delete client")
    void testDeleteClient() {
        given(clientRepository.existsById(1L)).willReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("test get all clients")
    void testGetAllClients() {
        given(clientRepository.findAll()).willReturn(Collections.singletonList(client));
        given(modelMapper.map(client, ClientDTO.class)).willReturn(clientDTO);

        assertEquals(Collections.singletonList(clientDTO), clientService.getAllClient());
    }
}
