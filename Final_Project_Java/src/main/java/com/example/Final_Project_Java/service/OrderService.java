package com.example.Final_Project_Java.service;

import com.example.Final_Project_Java.model.Order;
import com.example.Final_Project_Java.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    public List<Order> getAllOrdersDesc() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

}
