package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Cart;
import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.repository.FoodRepository;
import com.example.Final_Project_Java.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cartItems", cart.getItems());//list
        model.addAttribute("totalPrice", cart.getItems().stream()
                .mapToDouble(item -> item.getFood().getPrice() * item.getQuantity())
                .sum());
        return "Restaurant/cart";
    }

    @PostMapping("/add/{foodId}")
    public String addToCart(@PathVariable String foodId,
                          @RequestParam(defaultValue = "1") int quantity) {
        Food food = foodRepository.findById(foodId).orElseThrow();
        cartService.addItem(food, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String foodId) {
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
