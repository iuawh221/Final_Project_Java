package com.example.Final_Project_Java.VNPay;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/create")
    public PaymentResponse createPayment(
            @RequestParam double amount,
            @RequestParam String orderInfo,
            HttpServletRequest request) throws Exception {

        String ip = request.getRemoteAddr();
        String paymentUrl = vnPayService.createPaymentUrl(amount, orderInfo, ip);

        return new PaymentResponse(paymentUrl);
    }
    @GetMapping("/return")
    public ModelAndView paymentReturn(HttpServletRequest request) {

        Map<String, String[]> params = request.getParameterMap();
        String responseCode = request.getParameter("vnp_ResponseCode");

        ModelAndView mv = new ModelAndView("payment_result");

        if ("00".equals(responseCode)) {
            mv.addObject("message", "Thanh toán thành công!");
            mv.addObject("status", true);
        } else {
            mv.addObject("message", "Thanh toán thất bại!");
            mv.addObject("status", false);
        }
        return mv;
    }

}
