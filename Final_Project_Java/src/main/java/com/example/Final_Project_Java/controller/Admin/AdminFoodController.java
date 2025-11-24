package com.example.Final_Project_Java.controller.Admin;

import com.example.Final_Project_Java.model.Category;
import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.CategoryRepository;
import com.example.Final_Project_Java.repository.FoodRepository;
import com.example.Final_Project_Java.service.CategoryService;
import com.example.Final_Project_Java.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/food")
public class AdminFoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @GetMapping
    public String listFoods(Model model) {
        List<Food> foods = foodService.findAll();
        model.addAttribute("foods", foods);
        return "Restaurant/admin/adminFood"; // Thymeleaf template
    }
    @GetMapping("/add")
    public String addFoodForm(Model model) {
        model.addAttribute("food", new Food());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "Restaurant/admin/adminAddFood"; // Thymeleaf template
    }
    @PostMapping("/add")
    public String saveFood(@ModelAttribute("food") Food food) {
        foodService.save(food);
        return "redirect:/admin/food";
    }
    @GetMapping("/delete/{id}")
    public String deleteFood(@PathVariable("id") String id) {
        foodService.delete(id);
        return "redirect:/admin/food";
    }
    @GetMapping("/search")
    public String searchFood(@RequestParam("keyword") String keyword, Model model) {
        List<Food> foods = foodRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("foods", foods);
        return "Restaurant/admin/adminFood";
    }
    @GetMapping("/edit/{id}")
    public String editFoodForm(@PathVariable String id, Model model) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("food", food);
        model.addAttribute("categories", categories);

        return "Restaurant/admin/adminEditFood";
    }


    @PostMapping("/edit/{id}")
    public String editFoodSubmit(@PathVariable String id, @ModelAttribute("food") Food food) {
        food.setId(id);
        foodRepository.save(food);
        return "redirect:/admin/food";
    }

}
