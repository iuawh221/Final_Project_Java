package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    FoodRepository foodRepository;
    @GetMapping("/menu")
    public String getMenu(Model model){
        List<Food> foods= foodRepository.findAll();
        model.addAttribute("foods", foods);
        return "Restaurant/menu";
    }
}
