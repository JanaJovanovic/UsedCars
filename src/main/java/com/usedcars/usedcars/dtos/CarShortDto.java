package com.usedcars.usedcars.dtos;

import lombok.Data;

@Data
public class CarShortDto {
    private String id;
    private String model;
    private Integer year;
    private Integer price;
    private String picture;
}
