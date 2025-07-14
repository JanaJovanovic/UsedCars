package com.usedcars.usedcars.service.impl;

import com.usedcars.usedcars.dtos.CarDto;
import com.usedcars.usedcars.dtos.CarRequestDto;
import com.usedcars.usedcars.dtos.CarShortDto;
import com.usedcars.usedcars.mappers.CarDtoMapper;
import com.usedcars.usedcars.model.Car;
import com.usedcars.usedcars.model.Picture;
import com.usedcars.usedcars.model.User;
import com.usedcars.usedcars.repository.CarRepository;
import com.usedcars.usedcars.repository.UserRepository;
import com.usedcars.usedcars.service.CarService;
import com.usedcars.usedcars.service.PictureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarDtoMapper carDtoMapper;
    private final PictureService pictureService;

    @Override
    @Transactional
    public Car addNewCar(Long userId,CarRequestDto carRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with " + userId + " doesn't exist!"));
        Car car = carDtoMapper.dtoToCar(carRequestDto);
        if (car.getModel() == "" || car.getYear() == null || car.getMileage() == null || car.getPrice() == null
        || car.getDriveType() == "" || car.getGearboxType() == "" || car.getDescription() == "" || car.getPictures().size() == 0){
            throw new IllegalStateException("You have some empty values.");
        }
        car.setUser(user);
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public Car editCar(Long userId, UUID id, CarRequestDto carRequestDto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Car with id " + id + " does not exist"));
        if (car.getUser().getId() != userId){
            throw new IllegalStateException("User with ID: " + userId + " does not have cat with ID: " + id) ;
        }
        Car newCar = carDtoMapper.dtoToCar(carRequestDto);
        Integer year = newCar.getYear();
        if (year != null){
            car.setYear(year);
        }

        Integer price = newCar.getPrice();
        if (price != null){
            car.setPrice(price);
        }

        String model = newCar.getModel();
        if (model != null){
            car.setModel(model);
        }

        Integer mileage = newCar.getMileage();
        if (mileage != null){
            car.setMileage(mileage);
        }

        String driveType = newCar.getDriveType();
        if (driveType != null){
            car.setDriveType(driveType);
        }

        String gearBoxType = newCar.getGearboxType();
        if (gearBoxType != null){
            car.setGearboxType(gearBoxType);
        }

        String description = newCar.getDescription();
        if (description != null){
            car.setDescription(description);
        }

        Set<Picture> pictures = newCar.getPictures();
        if (pictures != null){
            pictureService.deleteByCar(car.getId());
            car.setPictures(pictures);
        }

        return carRepository.save(car);
    }

    @Override
    public CarDto findCar(UUID id) {
        return carDtoMapper.carToDto(carRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Car with id " + id + " does not exist")));
    }



    @Override
    @Transactional
    public void deleteCar(Long userId,UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Car with id " + id + " does not exist"));
//        if (car.getUser().getId() != userId){
//            throw new IllegalStateException("User with ID: " + userId + " does not have car with ID: " + id) ;
//        }
        pictureService.deleteByCar(id);
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void approveCar(Long userId, UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Car with id " + id + " does not exist"));
        car.setApproved(true);
        carRepository.save(car);
    }

    @Override
    public Page<CarShortDto> getPageOfApprovedCars(Pageable pageable, String model, Integer startYear, Integer endYear, Integer mileage, Integer startPrice, Integer endPrice, String driveType, String gearBoxType) {
        Page page = carRepository.findAllApproved(pageable,model == null ? null : model.toLowerCase(),startYear,endYear,mileage,startPrice,endPrice,driveType,gearBoxType);
        return new PageImpl<>(carDtoMapper.carToShortDto(page.getContent()),pageable, page.getTotalElements());
    }

    @Override
    public Page<CarShortDto> getPageOfNonApprovedCars(Pageable pageable, String model, Integer startYear, Integer endYear, Integer mileage, Integer startPrice, Integer endPrice, String driveType, String gearBoxType) {
        Page page = carRepository.findAllNonApproved(pageable,model == null ? null : model.toLowerCase(),startYear,endYear,mileage,startPrice,endPrice,driveType,gearBoxType);
        return new PageImpl<>(carDtoMapper.carToShortDto(page.getContent()),pageable, page.getTotalElements());
    }
}
