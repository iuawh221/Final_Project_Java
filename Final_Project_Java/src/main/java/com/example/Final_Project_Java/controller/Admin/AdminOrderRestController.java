package com.example.Final_Project_Java.controller.Admin;

import com.example.Final_Project_Java.model.Order;
import com.example.Final_Project_Java.repository.OrderRepository;
import com.example.Final_Project_Java.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/orders")
@Tag(name = "Admin Orders", description = "API quản lý đơn hàng cho admin")
public class AdminOrderRestController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public AdminOrderRestController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Operation(summary = "Lấy tất cả đơn hàng", description = "Trả về danh sách đơn hàng sắp xếp giảm dần theo ngày tạo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh sách đơn hàng trả về thành công")
    })
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrdersDesc();
    }

    @Operation(summary = "Lấy đơn hàng theo ID", description = "Trả về chi tiết đơn hàng theo mã ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đơn hàng tìm thấy"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(
            @Parameter(description = "Mã đơn hàng cần tìm", required = true)
            @PathVariable String orderId) {
        return orderRepository.findById(orderId);
    }

    @Operation(summary = "Cập nhật trạng thái đơn hàng", description = "Thay đổi trạng thái đơn hàng theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật trạng thái thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @PutMapping("/{orderId}/status")
    public Order updateStatus(
            @Parameter(description = "Mã đơn hàng cần cập nhật", required = true)
            @PathVariable String orderId,
            @Parameter(description = "Trạng thái mới của đơn hàng", required = true)
            @RequestParam String status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Operation(summary = "Tìm kiếm đơn hàng", description = "Tìm kiếm đơn hàng theo mã ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đơn hàng tìm thấy"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đơn hàng")
    })
    @GetMapping("/search")
    public Optional<Order> searchOrder(
            @Parameter(description = "Mã đơn hàng cần tìm", required = true)
            @RequestParam("orderId") String orderId) {
        return orderRepository.findById(orderId);
    }
}
