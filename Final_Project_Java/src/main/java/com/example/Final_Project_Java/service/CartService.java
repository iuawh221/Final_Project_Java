package com.example.Final_Project_Java.service;

import com.example.Final_Project_Java.model.Cart;
import com.example.Final_Project_Java.model.CartItem;
import com.example.Final_Project_Java.model.Food;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Service
@SessionScope
public class CartService {
    private Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

    public void addItem(Food food, int quantity) {
        cart.addItem(food, quantity);
    }

    public void removeItem(String foodId) {
        cart.removeItem(foodId);
    }

    public void updateItem(String foodId, int quantity) {
        cart.updateItem(foodId, quantity);
    }

    public void clearCart() {
        cart.clear();
    }




}

