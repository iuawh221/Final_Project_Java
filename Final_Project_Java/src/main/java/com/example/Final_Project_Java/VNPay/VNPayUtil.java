package com.example.Final_Project_Java.VNPay;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VNPayUtil {

    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
            hmac.init(secretKey);
            byte[] hash = hmac.doFinal(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi mã hóa HmacSHA512", e);
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return ip == null ? request.getRemoteAddr() : ip;
    }
}

