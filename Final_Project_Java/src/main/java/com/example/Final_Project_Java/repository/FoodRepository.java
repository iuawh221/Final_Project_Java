package com.example.Final_Project_Java.repository;

import com.example.Final_Project_Java.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food,String> {


    List<Food> findByNameContainingIgnoreCase(String keyword);
}
