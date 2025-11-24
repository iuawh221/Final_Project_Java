package com.example.Final_Project_Java.service;

import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public void save(Food food) {
        foodRepository.save(food);
    }

    public void delete(String id) {
        foodRepository.deleteById(id);
    }
}
