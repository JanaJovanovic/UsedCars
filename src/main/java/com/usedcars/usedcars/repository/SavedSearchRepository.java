package com.usedcars.usedcars.repository;


import com.usedcars.usedcars.model.SavedSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SavedSearchRepository extends JpaRepository<SavedSearch,Long> {


    @Modifying
    @Query(value = "INSERT INTO saved_search (user_id, saved_search) VALUES (:userId, :savedSearch)", nativeQuery = true)
    void addSavedSearch(@Param("userId") Long userId, @Param("savedSearch") String savedSearch);


    @Query(value = "SELECT saved_search FROM saved_search WHERE user_id = :userId ORDER BY id DESC LIMIT 10;",nativeQuery = true)
    List<String> findTop10ByUserIdOrderByidDesc(@Param("userId") Long userId);
}

