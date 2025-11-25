package com.example.Final_Project_Java.repository;

import com.example.Final_Project_Java.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food,String> {


    List<Food> findByNameContainingIgnoreCase(String keyword);

    List<Food> findByCategory(String category);

    @Query("SELECT DISTINCT f.category FROM Food f WHERE f.category IS NOT NULL")
    List<String> findDistinctCategories();

    Page<Food> findByCategory(String category, Pageable pageable);

    List<Food> findTop5ByCategoryAndIdNot(String category, String excludeId);
}
