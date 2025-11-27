package com.example.Final_Project_Java.repository;

import com.example.Final_Project_Java.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends MongoRepository<Contact , String> {
    List<Contact> findByNameIgnoreCaseContaining(String keyword);
}
