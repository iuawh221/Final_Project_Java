package com.example.Final_Project_Java.controller.Admin;


import com.example.Final_Project_Java.repository.ReservationRepository;
import com.example.Final_Project_Java.service.OrderService;
import com.example.Final_Project_Java.service.ResercationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AdminIndexController {

    @Autowired
    OrderService orderService;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ResercationService resercationService;
    @GetMapping("/admin/index")
    public String getAdminIndex(Model model){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String today = LocalDate.now().format(formatter);

        long count = reservationRepository.countByDate(today);
        model.addAttribute("todayBookings", count);

        long preparing = resercationService.countByStatus("Đang chuẩn bị");
        model.addAttribute("preparing", preparing);

        long shipping = resercationService.countByStatus("Đang giao");
        model.addAttribute("shipping", shipping);


        return "Restaurant/admin/adminIndex";
    }
}
