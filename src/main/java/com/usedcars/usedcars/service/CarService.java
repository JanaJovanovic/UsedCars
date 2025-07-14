package com.usedcars.usedcars.service;

import com.usedcars.usedcars.dtos.CarDto;
import com.usedcars.usedcars.dtos.CarRequestDto;
import com.usedcars.usedcars.dtos.CarShortDto;
import com.usedcars.usedcars.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CarService {
    Car addNewCar(Long userId,CarRequestDto carRequestDto);
    Car editCar(Long userId, UUID id, CarRequestDto carRequestDto);
    CarDto findCar(UUID id);
    void deleteCar(Long userId, UUID id);

    void approveCar(Long userId, UUID id);

    Page<CarShortDto> getPageOfApprovedCars(Pageable pageable, String model, Integer startYear, Integer endYear, Integer mileage, Integer startPrice, Integer endPrice, String driveType, String gearBoxType);

    Page<CarShortDto> getPageOfNonApprovedCars(Pageable pageable, String model, Integer startYear, Integer endYear, Integer mileage, Integer startPrice, Integer endPrice, String driveType, String gearBoxType);
}

