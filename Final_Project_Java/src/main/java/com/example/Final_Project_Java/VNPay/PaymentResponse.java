package com.example.Final_Project_Java.VNPay;

public class PaymentResponse {
    private String paymentUrl;

    public PaymentResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }
}
