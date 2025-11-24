package com.example.Final_Project_Java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "food")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    private String id;
    private String name;
    private String description;
    private String detail;
    private String imageUrl;
    private double price;
    private String category;
}
