package com.example.Final_Project_Java.repository;

import com.example.Final_Project_Java.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findByNameContainingIgnoreCase(String keyword);
}
