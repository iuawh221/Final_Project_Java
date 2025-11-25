package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.FoodRepository;
import com.example.Final_Project_Java.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    FoodService foodService;
    @GetMapping("/index")
    public String getIndex(Model model){
        List<Food> foods = foodService.getCheapestFoods(6);
        model.addAttribute("foods", foods);


        return "Restaurant/index";
    }
}
