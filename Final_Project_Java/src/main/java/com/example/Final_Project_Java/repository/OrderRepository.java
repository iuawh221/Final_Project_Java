package com.example.Final_Project_Java.repository;

import com.example.Final_Project_Java.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
