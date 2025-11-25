package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @GetMapping("/{id}")
    public String getFoodDetail(@PathVariable String id, Model model) {

        Food food = foodService.getFoodById(id);

        List<Food> relatedFoods = foodService.getRelatedFoods(food.getCategory(), food.getId());

        // Đưa dữ liệu ra view
        model.addAttribute("food", food);
        model.addAttribute("relatedFoods", relatedFoods);

        return "Restaurant/foodDetail";
    }
}
