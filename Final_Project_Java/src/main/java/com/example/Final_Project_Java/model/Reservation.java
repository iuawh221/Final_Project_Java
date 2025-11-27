package com.example.Final_Project_Java.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reservation")
public class Reservation {

    @Id
    private String id;

    private String name;
    private String date;
    private String time;
    private int guests;
}

