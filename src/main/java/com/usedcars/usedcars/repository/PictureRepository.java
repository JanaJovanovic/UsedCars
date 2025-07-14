package com.usedcars.usedcars.repository;

import com.usedcars.usedcars.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {


    @Modifying
    @Query(value = "DELETE FROM picture WHERE car_id = :id", nativeQuery = true)
    void deleteByCar(String id);

    @Query(value = "SELECT picture FROM picture WHERE car_id = :id",nativeQuery = true)
    List<String> findByCar(String id);

}
