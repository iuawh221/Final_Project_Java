package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Cart;
import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.FoodRepository;
import com.example.Final_Project_Java.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart/index"; // giao diện giỏ hàng
    }

    @PostMapping("/add/{foodId}")
    @ResponseBody
    public Cart addToCart(@PathVariable String foodId,
                          @RequestParam(defaultValue = "1") int quantity) {
        Food food = foodRepository.findById(foodId).orElseThrow();
        cartService.addItem(food, quantity);
        return cartService.getCart();
    }

    @GetMapping("/remove/{foodId}")
    public String removeFromCart(@PathVariable String foodId) {
        cartService.removeItem(foodId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{foodId}")
    public String updateQuantity(@PathVariable String foodId,
                                 @RequestParam int quantity) {
        cartService.updateItem(foodId, quantity);
        return "redirect:/cart";
    }
}
