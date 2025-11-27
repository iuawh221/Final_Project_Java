package com.example.Final_Project_Java.controller.Admin;

import com.example.Final_Project_Java.model.Reservation;
import com.example.Final_Project_Java.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "Restaurant/admin/adminBooking";
    }
    @GetMapping("/search")
    public String searchBookings(@RequestParam("keyword") String keyword, Model model) {
        List<Reservation> results = reservationRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("reservations", results);
        model.addAttribute("keyword", keyword);
        return "Restaurant/admin/adminBooking";
    }
}
