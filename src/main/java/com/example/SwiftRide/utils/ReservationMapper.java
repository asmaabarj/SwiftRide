package com.example.SwiftRide.utils;

import com.example.SwiftRide.dto.ReservationDTO.ReservationRequestDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationResponseDTO;

import com.example.SwiftRide.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "driverId", target = "driver.id")
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "vehicleId", target = "vehicle.id")
    Reservation toReservation(ReservationRequestDTO dto);

    @Mapping(source = "driver", target = "driverDTO")
    @Mapping(source = "client", target = "clientDTO")
    @Mapping(source = "vehicle", target = "vehicleDTO")
    ReservationResponseDTO toResponseDTO(Reservation reservation);

    void updateEntityFromDTO(ReservationRequestDTO reservationRequestDTO, @MappingTarget Reservation reservation);

}

