package com.example.Final_Project_Java.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {
    @Autowired
    private Environment env;
    final String GEMINI_API_URI = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=";

    public String sendRequest2Gemini(String query) {
        try {
            // build JSON body
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode textNode = mapper.createObjectNode();
            textNode.put("text", query);
            ArrayNode partsArray = mapper.createArrayNode();
            partsArray.add(textNode);
            ObjectNode contentsNode = mapper.createObjectNode();
            contentsNode.set("parts", partsArray);
            ObjectNode root = mapper.createObjectNode();
            root.set("contents", contentsNode);

            String jsonBody = mapper.writeValueAsString(root);

            // gửi request
            RestTemplate restTemplate = new RestTemplate();
            String data = restTemplate.postForObject(
                    GEMINI_API_URI + env.getProperty("gemini_api_key"),
                    new HttpEntity<>(jsonBody, new HttpHeaders(){{setContentType(MediaType.APPLICATION_JSON);}}),
                    String.class
            );

            JsonNode jsonData = mapper.readTree(data);
            return jsonData.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

