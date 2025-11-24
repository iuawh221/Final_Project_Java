package com.example.Final_Project_Java.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Food food;
    private int quantity;

    public double getTotalPrice() {
        return food.getPrice() * quantity;
    }
}
