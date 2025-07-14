package com.usedcars.usedcars.model;

import lombok.*;

import jakarta.persistence.*;


@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String picture;
}
