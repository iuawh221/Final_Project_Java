package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Category;
import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.CategoryRepository;
import com.example.Final_Project_Java.repository.FoodRepository;
import com.example.Final_Project_Java.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class MenuController {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/menu")
    public String getMenu(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 9;
        Page<Food> foodPage = foodService.getFoodsByCategory(category, page , pageSize);

        model.addAttribute("foods", foodPage.getContent());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodPage.getTotalPages());

        return "Restaurant/menu";
    }
    @GetMapping("/menu/detail")
    public String getFoodDetail(@RequestParam String id, Model model) {

        Food food = foodService.getFoodById(id);

        List<Food> relatedFoods = foodService.getRelatedFoods(food.getCategory(), food.getId());

        // Đưa dữ liệu ra view
        model.addAttribute("food", food);
        model.addAttribute("relatedFoods", relatedFoods);

        return "Restaurant/foodDetail";
    }

}
