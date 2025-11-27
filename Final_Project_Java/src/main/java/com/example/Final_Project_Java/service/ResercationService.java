package com.example.Final_Project_Java.service;


import com.example.Final_Project_Java.model.Order;
import com.example.Final_Project_Java.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ResercationService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public long countByStatus(String status) {
        Criteria criteria = Criteria.where("status").is(status);
        return mongoTemplate.count(Query.query(criteria), Order.class);
    }
}
