package com.example.Final_Project_Java.controller.Admin;

import com.example.Final_Project_Java.model.Order;
import com.example.Final_Project_Java.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "Restaurant/admin/adminOrder"; // trỏ tới template admin/orders.html
    }

    // Cập nhật trạng thái đơn hàng
    @PostMapping("/update-status/{orderId}")
    public String updateStatus(@PathVariable String orderId,
                               @RequestParam String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
        return "redirect:/admin/orders"; // quay lại danh sách đơn hàng
    }
    @GetMapping("/search")
    public String searchOrder(@RequestParam("orderId") String orderId, Model model) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            model.addAttribute("orders", List.of(orderOpt.get())); // hiển thị 1 đơn hàng
        } else {
            model.addAttribute("orders", List.of()); // không tìm thấy
            model.addAttribute("message", "Không tìm thấy đơn hàng với mã: " + orderId);
        }
        return "Restaurant/admin/adminOrder";
    }
}
