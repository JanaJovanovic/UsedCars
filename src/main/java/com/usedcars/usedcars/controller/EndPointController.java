package com.usedcars.usedcars.controller;

import com.usedcars.usedcars.auth.AuthenticationService;
import com.usedcars.usedcars.config.LogoutService;
import com.usedcars.usedcars.dtos.CarDto;
import com.usedcars.usedcars.dtos.CarShortDto;
import com.usedcars.usedcars.dtos.UserDto;
import com.usedcars.usedcars.model.User;
import com.usedcars.usedcars.service.CarService;
import com.usedcars.usedcars.service.SavedSearchService;
import com.usedcars.usedcars.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Controller
@AllArgsConstructor
public class EndPointController {
    private final LogoutService logoutService;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final CarService carService;
    private final SavedSearchService savedSearchService;
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @GetMapping("/addCar")
    public String addCar() {
        return "addCar";
    }

    @GetMapping(value = {"/home", "/", ""})
    public String home() {
        return "home";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logoutService.logout(request, response, authentication);
        return "redirect:/home"; // Redirect to the home page or any other desired page
    }


    @GetMapping("/users")
    public String getUsers(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                           @RequestParam(name = "pageSize", defaultValue = "50")  Integer pageSize,
                           Model model){
        Page<UserDto> userPage = userService.getAllUsers(PageRequest.of(pageNumber, pageSize));
        model.addAttribute("userPage",userPage);
        return "users";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/car/{id}")
    public String getCarDetails(@PathVariable UUID id, Model model) {
        CarDto carDto = carService.findCar(id);
        model.addAttribute("car", carDto);
        return "car-details";
    }

    @GetMapping("/car")
    public String getPageOfApprovedCars(HttpServletRequest httpServletRequest,
                                @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                @RequestParam(defaultValue = "id,desc") String sort,
                                @RequestParam(required = false) String model,
                                @RequestParam(required = false) Integer startYear,
                                @RequestParam(required = false) Integer endYear,
                                @RequestParam(required = false) Integer mileage,
                                @RequestParam(required = false) Integer startPrice,
                                @RequestParam(required = false) Integer endPrice,
                                @RequestParam(required = false) String driveType,
                                @RequestParam(required = false) String gearBoxType,
                                Model model2) throws IOException {
        List<Sort.Order> orders = new ArrayList<>();
//        User user = authenticationService.getUserOrNot(httpServletRequest);
//        if (user != null) {
//            Long userId = user.getId();
//            savedSearchService.addSavedSearch(userId, model, startYear, endYear, mileage, startPrice, endPrice, driveType, gearBoxType);
//        }

        String[] sortParams = sort.split(",");
        for (int i = 0; i < sortParams.length; i += 2) {
            String sortField = sortParams[i];
            Sort.Direction sortDirection = (i + 1 < sortParams.length && sortParams[i + 1].equalsIgnoreCase("asc")) ?
                    Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Sort.Order(sortDirection, sortField));
        }

        Page<CarShortDto> carsPage = carService.getPageOfApprovedCars(PageRequest.of(pageNumber, pageSize, Sort.by(orders)), model, startYear, endYear, mileage, startPrice, endPrice, driveType, gearBoxType);
        if (carsPage.isEmpty()) {
            carsPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, pageSize), 0);
        }
        model2.addAttribute("carsPage", carsPage); // Add the Page object as an attribute

        return "cars";
    }
    @GetMapping("/admin/car")
    public String getPageOfNonApprovedCars(HttpServletRequest httpServletRequest,
                                        @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                        @RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
                                        @RequestParam(defaultValue = "id,desc") String sort,
                                        @RequestParam(required = false) String model,
                                        @RequestParam(required = false) Integer startYear,
                                        @RequestParam(required = false) Integer endYear,
                                        @RequestParam(required = false) Integer mileage,
                                        @RequestParam(required = false) Integer startPrice,
                                        @RequestParam(required = false) Integer endPrice,
                                        @RequestParam(required = false) String driveType,
                                        @RequestParam(required = false) String gearBoxType,
                                        Model model2) throws IOException {
        List<Sort.Order> orders = new ArrayList<>();
        User user = authenticationService.getUserOrNot(httpServletRequest);
//        if (user != null) {
//            Long userId = user.getId();
//            savedSearchService.addSavedSearch(userId, model, startYear, endYear, mileage, startPrice, endPrice, driveType, gearBoxType);
//        }

        String[] sortParams = sort.split(",");
        for (int i = 0; i < sortParams.length; i += 2) {
            String sortField = sortParams[i];
            Sort.Direction sortDirection = (i + 1 < sortParams.length && sortParams[i + 1].equalsIgnoreCase("asc")) ?
                    Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Sort.Order(sortDirection, sortField));
        }

        Page<CarShortDto> carsPage = carService.getPageOfNonApprovedCars(PageRequest.of(pageNumber, pageSize, Sort.by(orders)), model, startYear, endYear, mileage, startPrice, endPrice, driveType, gearBoxType);
        model2.addAttribute("carsPage", carsPage); // Add the Page object as an attribute

        return "admin-cars";
    }
}