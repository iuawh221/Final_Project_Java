package com.example.Final_Project_Java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private String customerName;
    private String phone;
    private String address;
    private List<OrderItem> items;
    private double totalAmount;
    private String status; // Đang chuẩn bị, Đang giao, Đã giao, Đã hủy
    private LocalDateTime createdAt;


}
