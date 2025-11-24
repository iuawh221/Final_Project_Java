package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    @ResponseBody
    public ResponseEntity<String> askAIChat(@RequestParam String question) {
        if (question != null && !question.isEmpty()) {
            String output = chatService.sendRequest2Gemini(question);
            return ResponseEntity.ok(output);
        }
        return ResponseEntity.badRequest().body("Empty query");
    }



    @GetMapping("/admin/chat")
    public String chatPage() {
        return "Restaurant/chatBox";
    }
}
