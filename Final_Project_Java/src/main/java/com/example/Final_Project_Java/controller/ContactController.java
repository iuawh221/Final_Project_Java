package com.example.Final_Project_Java.controller;


import com.example.Final_Project_Java.model.Contact;
import com.example.Final_Project_Java.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @Autowired
    ContactService contactService;
    @GetMapping("/contact-us")
    public String getContact(){
        return "Restaurant/contact-us";
    }
    @PostMapping("/contact-us")
    public String submitContactForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message
    ) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setSubject(subject);
        contact.setMessage(message);

        contactService.saveContact(contact);

        return "redirect:/contact-us";
    }
}
