package com.usedcars.usedcars.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private String model;
    private Integer year;
    private Integer mileage;
    private Integer price;
    private String driveType;
    private String gearboxType;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="car_id")
    private Set<Picture> pictures = new LinkedHashSet<>();
    private boolean approved = false;

}
