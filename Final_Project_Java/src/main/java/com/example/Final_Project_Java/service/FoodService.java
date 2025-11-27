package com.example.Final_Project_Java.service;

import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    public List<Food> getCheapestFoods(int limit) {
        return foodRepository.findAll().stream()
                .sorted(Comparator.comparing(Food::getPrice))//.reversed() giam dan
                .limit(limit)
                .toList();
    }
    public List<Food> getFoodsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return foodRepository.findAll();
        }
        return foodRepository.findByCategory(category);
    }
    public Page<Food> getFoodsByCategory(String category, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        if (category == null || category.isEmpty()) {
            return foodRepository.findAll(pageable);
        }
        return foodRepository.findByCategory(category, pageable);
    }
    public Food getFoodById(String id) {
        return foodRepository.findById(id).orElse(null);
    }

    public List<Food> getRelatedFoods(String category, String excludeId) {
        return foodRepository.findTop5ByCategoryAndIdNot(category, excludeId);
    }
    public List<Food> getTopNewestFoods(int i) {
        return foodRepository.findAll(
                PageRequest.of(0, i, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent();
    }

}
