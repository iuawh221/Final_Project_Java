package com.example.Final_Project_Java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String foodId;
    private String foodName;
    private int quantity;
    private double price;

    public double getTotalPrice() {
        return price * quantity;
    }

}
