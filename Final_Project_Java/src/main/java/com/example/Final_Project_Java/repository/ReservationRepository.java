package com.example.Final_Project_Java.repository;

import com.example.Final_Project_Java.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends MongoRepository<Reservation,String> {

    Optional<Object> findById(Integer bookingId);

    List<Reservation> findByNameContainingIgnoreCase(String keyword);
}
