package com.usedcars.usedcars.controller;


import com.usedcars.usedcars.auth.AuthenticationService;
import com.usedcars.usedcars.dtos.CarRequestDto;
import com.usedcars.usedcars.service.CarService;
import com.usedcars.usedcars.service.SavedSearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/car")
@AllArgsConstructor
//@RestController
public class CarController {
        private final CarService carService;
        private final AuthenticationService authenticationService;
        private final SavedSearchService savedSearchService;
//    @GetMapping("/{id}")
//    public String getCarDetails(@PathVariable UUID id, Model model) {
//        CarDto carDto = carService.findCar(id);
//        model.addAttribute("car", carDto);
//        return "car-details";
//    }
//
//    @GetMapping
//    public String getPageOfCars(HttpServletRequest httpServletRequest,
//                                @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
//                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
//                                @RequestParam(defaultValue = "id,desc") String sort,
//                                @RequestParam(required = false) String model,
//                                @RequestParam(required = false) Integer startYear,
//                                @RequestParam(required = false) Integer endYear,
//                                @RequestParam(required = false) Integer mileage,
//                                @RequestParam(required = false) Integer startPrice,
//                                @RequestParam(required = false) Integer endPrice,
//                                @RequestParam(required = false) String driveType,
//                                @RequestParam(required = false) String gearBoxType,
//                                Model model2) throws IOException {
//        List<Order> orders = new ArrayList<>();
//        User user = authenticationService.getUserOrNot(httpServletRequest);
//        if (user != null) {
//            Long userId = user.getId();
//            savedSearchService.addSavedSearch(userId, model, startYear, endYear, mileage, startPrice, endPrice, driveType, gearBoxType);
//        }
//
//        String[] sortParams = sort.split(",");
//        for (int i = 0; i < sortParams.length; i += 2) {
//            String sortField = sortParams[i];
//            Sort.Direction sortDirection = (i + 1 < sortParams.length && sortParams[i + 1].equalsIgnoreCase("asc")) ?
//                    Sort.Direction.ASC : Sort.Direction.DESC;
//            orders.add(new Order(sortDirection, sortField));
//        }
//
//        Page<CarShortDto> carsPage = carService.getPageOfCars(PageRequest.of(pageNumber, pageSize, Sort.by(orders)), model, startYear, endYear, mileage, startPrice, endPrice, driveType, gearBoxType);
//        model2.addAttribute("carsPage", carsPage); // Add the Page object as an attribute
//
//        return "cars";
//    }
    @PostMapping()
    public String addNewCar(HttpServletRequest httpServletRequest, @RequestBody CarRequestDto carRequestDto) throws IOException {
        Long id = authenticationService.getUser(httpServletRequest).getId();
        carService.addNewCar(id,carRequestDto);
        return "redirect:/car";
    }

//    @PutMapping("/{id}")
//    public Car editCar(HttpServletRequest httpServletRequest, @PathVariable UUID id, @RequestBody CarRequestDto carRequestDto) throws IOException {
//        Long userId = authenticationService.getUser(httpServletRequest).getId();
//        return carService.editCar(userId, id, carRequestDto);
//    }

    @DeleteMapping("/{id}")
    public void deleteCar(HttpServletRequest httpServletRequest,@PathVariable UUID id) throws IOException {
        Long userId = authenticationService.getUser(httpServletRequest).getId();
        carService.deleteCar(userId, id);
    }

    @PutMapping("/{id}")
        public void approveCar(HttpServletRequest httpServletRequest,@PathVariable UUID id) throws IOException {
        Long userId = authenticationService.getUser(httpServletRequest).getId();
        carService.approveCar(userId,id);
    }


}
