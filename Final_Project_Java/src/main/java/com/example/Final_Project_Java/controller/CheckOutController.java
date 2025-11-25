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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CheckOutController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderRepository orderRepository;
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        Cart cartItems = cartService.getCart();


        model.addAttribute("cartItems", cartItems.getItems());
        model.addAttribute("cartTotal",cartItems.getTotalPrice() );
        return "Restaurant/checkOut"; // file checkout.html
    }
    @GetMapping("/search")
    public String search(@RequestParam(name = "code", required = false) String code,
                         Model model) {

        if (code == null || code.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập mã đơn hàng");
            return "Restaurant/confirm";
        }

        Optional<Order> orderOptional = orderRepository.findById(code);

        if (orderOptional.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy đơn hàng với mã: " + code);
            model.addAttribute("order", null); // để view biết là không có order
            return "Restaurant/confirm";
        }

        model.addAttribute("order", orderOptional.get());
        return "Restaurant/confirm";
    }



    @PostMapping("/checkout")
    public String placeOrder(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getCart();

        // Gắn items từ Cart vào Order
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
        redirectAttributes.addFlashAttribute("orderId", order.getId());
        return "redirect:/thankyou";


    }
    @GetMapping("/thankyou")
    public String thankyou(@ModelAttribute("orderId") String orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElse(null);
        model.addAttribute("order", order);
        return "Restaurant/thankyou";
    }
}





