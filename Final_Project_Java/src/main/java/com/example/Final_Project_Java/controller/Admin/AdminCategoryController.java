package com.example.Final_Project_Java.controller.Admin;

import com.example.Final_Project_Java.model.Category;
import com.example.Final_Project_Java.repository.CategoryRepository;
import com.example.Final_Project_Java.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public String getAdminCategory(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "Restaurant/admin/adminCategory";
    }
    @GetMapping("/add")
    public String showAddForm() {
        return "Restaurant/admin/adminAddCategory";
    }
    @PostMapping("/add")
    public String createCategory(@RequestParam String name,
                                 @RequestParam String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category", category);
        return "Restaurant/admin/adminEditCategory";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable String id,
                                 @RequestParam String name,
                                 @RequestParam String description) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }
    @GetMapping("/search")
    public String searchCategories(@RequestParam("keyword") String keyword, Model model) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("categories", categories);
        return "Restaurant/admin/adminCategory"; // quay lại trang danh sách với kết quả lọc
    }

}
