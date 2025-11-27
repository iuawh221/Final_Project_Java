package com.example.Final_Project_Java.controller;


import com.example.Final_Project_Java.model.Order;
import com.example.Final_Project_Java.model.User;
import com.example.Final_Project_Java.repository.OrderRepository;
import com.example.Final_Project_Java.repository.UserRepository;
import com.example.Final_Project_Java.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
public class HomeController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/ping")
    public String pingMongo() {
        return mongoTemplate.getDb().getName(); // sẽ trả về "Final_Project"
    }

    @GetMapping("/admin/login")
    public String loginForm() {
        return "Restaurant/admin/adminLogin"; // Thymeleaf login page
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response) {

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return "redirect:/admin/login?error=usernotfound";
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            return "redirect:/admin/login?error=wrongpassword";
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());

        String token = JwtUtil.generateToken(claims);
        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(4 * 60 * 60);
        response.addCookie(jwtCookie);

        System.out.println(token);
        return "redirect:/admin/index";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Xóa cookie JWT
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // xóa ngay
        response.addCookie(cookie);

        return "redirect:/admin/login";
    }

    @Autowired
    OrderRepository orderRepository;
    @GetMapping("/admin/index")
    public String getIndexAdmin(){
        return "Restaurant/admin/adminIndex";
    }
    @GetMapping("/booking")
    public String getBooking(){
        return "Restaurant/booking";
    }
    @GetMapping("/contact-us")
    public String getContact(){
        return "Restaurant/contact-us";
    }
}
