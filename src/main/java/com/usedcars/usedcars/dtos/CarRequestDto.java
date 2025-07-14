package com.usedcars.usedcars.dtos;


import lombok.Data;

import java.util.Set;

@Data
public class CarRequestDto {

    private String model;
    private Integer year;
    private Integer mileage;
    private Integer price;
    private String driveType;
    private String gearboxType;
    private String description;
    private Set<String> pictures;

}
