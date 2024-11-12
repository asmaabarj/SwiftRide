package com.example.SwiftRide.utils;


import com.example.SwiftRide.dto.ReservationDTO;
import com.example.SwiftRide.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "departureAddress.city", target = "departureCity")
    @Mapping(source = "departureAddress.district", target = "departureDistrict")
    @Mapping(source = "arrivalAddress.city", target = "arrivalCity")
    @Mapping(source = "arrivalAddress.district", target = "arrivalDistrict")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    ReservationDTO reservationToReservationDTO(Reservation reservation);

    @Mapping(source = "departureCity", target = "departureAddress.city")
    @Mapping(source = "departureDistrict", target = "departureAddress.district")
    @Mapping(source = "arrivalCity", target = "arrivalAddress.city")
    @Mapping(source = "arrivalDistrict", target = "arrivalAddress.district")
    @Mapping(source = "driverId", target = "driver.id")
    @Mapping(source = "vehicleId", target = "vehicle.id")
    Reservation reservationDTOToReservation(ReservationDTO reservationDTO);
}
