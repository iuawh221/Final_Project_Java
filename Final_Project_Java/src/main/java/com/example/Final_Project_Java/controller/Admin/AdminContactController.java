package com.example.Final_Project_Java.controller.Admin;

import com.example.Final_Project_Java.model.Contact;
import com.example.Final_Project_Java.model.Reservation;
import com.example.Final_Project_Java.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/contact")
public class AdminContactController {
    @Autowired
    ContactService contactService;

    @GetMapping
    public String listContact(Model model) {
        model.addAttribute("contact", contactService.getAllContacts());
        return "Restaurant/admin/adminContact";
    }
    @GetMapping("/search")
    public String searchBookings(@RequestParam("keyword") String keyword, Model model) {
        List<Contact> results = contactService.searchByName(keyword);
        model.addAttribute("reservations", results);
        model.addAttribute("keyword", keyword);
        return "Restaurant/admin/adminBooking";
    }
}
