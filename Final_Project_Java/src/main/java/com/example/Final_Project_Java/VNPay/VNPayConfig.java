package com.example.Final_Project_Java.VNPay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vnpay")
@Data
public class VNPayConfig {
    private String tmnCode;
    private String hashSecret;
    private String payUrl;
    private String returnUrl;
}

