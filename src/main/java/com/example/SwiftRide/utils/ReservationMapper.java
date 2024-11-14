package com.example.SwiftRide.utils;


import com.example.SwiftRide.dto.ReservationDTO;
import com.example.SwiftRide.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    ReservationDTO reservationToReservationDTO(Reservation reservation);


    @Mapping(source = "driverId", target = "driver.id")
    @Mapping(source = "vehicleId", target = "vehicle.id")
    Reservation reservationDTOToReservation(ReservationDTO reservationDTO);
}
