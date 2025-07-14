package com.usedcars.usedcars.repository;

import com.usedcars.usedcars.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {


        @Query(value = "SELECT * FROM car " +
                "WHERE ((:model IS NULL) OR (:model IS NOT NULL AND lower(model) LIKE %:model%)) " +
                "AND ((:startYear IS NOT NULL AND :endYear IS NOT NULL AND year >= :startYear AND year <= :endYear) " +
                        "OR (:startYear IS NOT NULL AND :endYear IS NULL AND year = :startYear) " +
                        "OR (:startYear IS NULL AND :endYear IS NOT NULL AND year <= :endYear) " +
                        "OR (:startYear IS NULL AND :endYear IS NULL)) " +
                "AND mileage = (CASE WHEN :mileage IS NOT NULL THEN :mileage ELSE mileage END) " +
                "AND ((:startPrice IS NOT NULL AND :endPrice IS NOT NULL AND price >= :startPrice AND price <= :endPrice) " +
                        "OR (:startPrice IS NOT NULL AND :endPrice IS NULL AND price >= :startPrice) " +
                        "OR (:startPrice IS NULL AND :endPrice IS NOT NULL AND price <= :endPrice) " +
                        "OR (:startPrice IS NULL AND :endPrice IS NULL)) " +
                "AND drive_type = (CASE WHEN :driveType IS NOT NULL THEN :driveType ELSE drive_type END) " +
                "AND gearbox_type = (CASE WHEN :gearBoxType IS NOT NULL THEN :gearBoxType ELSE gearbox_type END) " +
                "AND approved = FALSE"
                ,nativeQuery = true)
        Page<Car> findAllNonApproved(Pageable pageable, String model, Integer startYear, Integer endYear, Integer mileage, Integer startPrice, Integer endPrice, String driveType, String gearBoxType);
        @Query(value = "SELECT * FROM car " +
                "WHERE ((:model IS NULL) OR (:model IS NOT NULL AND lower(model) LIKE %:model%)) " +
                "AND ((:startYear IS NOT NULL AND :endYear IS NOT NULL AND year >= :startYear AND year <= :endYear) " +
                "OR (:startYear IS NOT NULL AND :endYear IS NULL AND year = :startYear) " +
                "OR (:startYear IS NULL AND :endYear IS NOT NULL AND year <= :endYear) " +
                "OR (:startYear IS NULL AND :endYear IS NULL)) " +
                "AND mileage = (CASE WHEN :mileage IS NOT NULL THEN :mileage ELSE mileage END) " +
                "AND ((:startPrice IS NOT NULL AND :endPrice IS NOT NULL AND price >= :startPrice AND price <= :endPrice) " +
                "OR (:startPrice IS NOT NULL AND :endPrice IS NULL AND price >= :startPrice) " +
                "OR (:startPrice IS NULL AND :endPrice IS NOT NULL AND price <= :endPrice) " +
                "OR (:startPrice IS NULL AND :endPrice IS NULL)) " +
                "AND drive_type = (CASE WHEN :driveType IS NOT NULL THEN :driveType ELSE drive_type END) " +
                "AND gearbox_type = (CASE WHEN :gearBoxType IS NOT NULL THEN :gearBoxType ELSE gearbox_type END) " +
                "AND approved = TRUE"
                ,nativeQuery = true)
        Page<Car> findAllApproved(Pageable pageable, String model, Integer startYear, Integer endYear, Integer mileage, Integer startPrice, Integer endPrice, String driveType, String gearBoxType);
}
