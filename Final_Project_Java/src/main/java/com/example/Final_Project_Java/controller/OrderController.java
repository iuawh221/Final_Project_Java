package com.example.Final_Project_Java.controller;

import com.example.Final_Project_Java.model.Cart;
import com.example.Final_Project_Java.model.Order;
import com.example.Final_Project_Java.model.OrderItem;
import com.example.Final_Project_Java.repository.OrderRepository;
import com.example.Final_Project_Java.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    // Hiển thị form đặt hàng (lấy dữ liệu từ giỏ hàng)
    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("order", new Order());
        return "order/checkout"; // giao diện checkout
    }

//    @PostMapping("/checkout")
//    public String placeOrder(@ModelAttribute Order order) {
//        Cart cart = cartService.getCart();
//
//        // Gắn items từ Cart vào Order
//        List<OrderItem> orderItems = cart.getItems().stream()
//                .map(item -> new OrderItem(item.getFood().getId(),
//                        item.getFood().getName(),
//                        item.getQuantity(),
//                        item.getFood().getPrice()))
//                .toList();
//
//        order.setItems(orderItems);
//        order.setTotalAmount(cart.getTotalPrice());
//        order.setStatus("Đang chuẩn bị");
//        order.setCreatedAt(LocalDateTime.now());
//
//        orderRepository.save(order);
//
//        cartService.clearCart();
//
//        return "redirect:/orders/confirm/" + order.getId();
//    }
    @PostMapping("/checkout")
    @ResponseBody
    public Order placeOrder(@RequestBody Order order) {
        Cart cart = cartService.getCart();

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(item -> new OrderItem(item.getFood().getId(),
                        item.getFood().getName(),
                        item.getQuantity(),
                        item.getFood().getPrice()))
                .toList();

        order.setItems(orderItems);
        order.setTotalAmount(cart.getTotalPrice());
        order.setStatus("Đang chuẩn bị");
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);
        cartService.clearCart();

        return order; // trả về JSON đơn hàng
    }



    // Trang xác nhận đơn hàng
    @GetMapping("/confirm/{orderId}")
    public String confirmOrder(@PathVariable String orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        model.addAttribute("order", order);
        return "order/confirm"; // giao diện xác nhận
    }

    // Admin xem danh sách đơn hàng
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "order/list"; // giao diện danh sách đơn hàng cho admin
    }

    // Admin đổi trạng thái đơn hàng
    @PostMapping("/update-status/{orderId}")
    public String updateStatus(@PathVariable String orderId,
                               @RequestParam String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
        return "redirect:/orders";
    }

}

