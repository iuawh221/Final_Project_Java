package com.example.Final_Project_Java.VNPay;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Service
public class VNPayService {

    private final VNPayConfig vnPayConfig;

    public VNPayService(VNPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public String createPaymentUrl(double amount, String orderInfo, String ipAddress) throws UnsupportedEncodingException {

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPAY yêu cầu nhân 100
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis()));
        params.put("vnp_OrderInfo", orderInfo);
        params.put("vnp_OrderType", "billpayment");
        params.put("vnp_Locale", "vn");
        params.put("vnp_IpAddr", ipAddress);
        params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());

        // Ngày giờ tạo request
        String createDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        params.put("vnp_CreateDate", createDate);

        // Tạo queryString và hashData
        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            query.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            query.append('=');
            query.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            query.append('&');

            hashData.append(entry.getKey());
            hashData.append('=');
            hashData.append(entry.getValue());
            hashData.append('&');
        }

        // Xóa dấu & cuối
        query.deleteCharAt(query.length() - 1);
        hashData.deleteCharAt(hashData.length() - 1);

        // Tạo chữ ký HMAC SHA512
        String secureHash = hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());

        return vnPayConfig.getPayUrl() + "?" + query + "&vnp_SecureHash=" + secureHash;
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac sha512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            sha512.init(secretKey);
            byte[] bytes = sha512.doFinal(data.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo HMAC SHA512", e);
        }
    }
}
