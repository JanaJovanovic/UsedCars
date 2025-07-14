package com.usedcars.usedcars.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavedSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String savedSearch;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
