package com.example.SwiftRide.repositories;

import com.example.SwiftRide.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
