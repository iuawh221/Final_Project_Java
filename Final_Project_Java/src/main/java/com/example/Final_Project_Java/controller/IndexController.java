package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Food;
import com.example.Final_Project_Java.model.Reservation;
import com.example.Final_Project_Java.repository.FoodRepository;
import com.example.Final_Project_Java.repository.ReservationRepository;
import com.example.Final_Project_Java.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    FoodService foodService;
    @Autowired
    ReservationRepository reservationRepository;
    @GetMapping("/index")
    public String getIndex(Model model){
        List<Food> foods = foodService.getCheapestFoods(6);
        model.addAttribute("foods", foods);

        List<Food> topUpdate = foodService.getTopNewestFoods(3);

        model.addAttribute("update", topUpdate);
        return "Restaurant/index";
    }

    @PostMapping("/book-table")
    public String bookTable(
            @RequestParam("name") String name,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("guests") int guests,
            RedirectAttributes redirectAttributes
    ) {
        // Tạo đối tượng Reservation từ dữ liệu form
        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setDate(date);
        reservation.setTime(time);
        reservation.setGuests(guests);

        reservationRepository.save(reservation);

        redirectAttributes.addAttribute("id", reservation.getId());
        // Redirect kèm id
        return "redirect:/booking-success";
    }

    @GetMapping("/booking-success")
    public String bookingSuccess(@RequestParam("id") String bookingId, Model model) {
        // Lấy thông tin đặt bàn từ database
        Reservation reservation = (Reservation) reservationRepository.findById(bookingId).orElse(null);

        if (reservation == null) {
            model.addAttribute("message", "Không tìm thấy thông tin đặt bàn!");
            return "booking-error";
        }

        // Gửi dữ liệu sang view
        model.addAttribute("booking", reservation);

        return "Restaurant/bookingSuccess";
    }





}
