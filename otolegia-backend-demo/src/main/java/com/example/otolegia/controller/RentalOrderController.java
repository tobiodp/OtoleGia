package com.example.otolegia.controller;

import com.example.otolegia.dto.ApiResponse;
import com.example.otolegia.dto.CreateRentalOrderRequest;
import com.example.otolegia.dto.RentalOrderResponse;
import com.example.otolegia.service.RentalOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API đặt xe và theo dõi đơn thuê.
 */
@RestController
@RequestMapping("/api/rental-orders")
@RequiredArgsConstructor
public class RentalOrderController {

    private final RentalOrderService rentalOrderService;

    @PostMapping
    public ApiResponse<RentalOrderResponse> createOrder(@Valid @RequestBody CreateRentalOrderRequest request) {
        return ApiResponse.ok("Đặt xe thành công, đơn đang chờ thanh toán", rentalOrderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<RentalOrderResponse> getOrderById(@PathVariable Long id) {
        return ApiResponse.ok("Lấy thông tin đơn thuê thành công", rentalOrderService.getOrderById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<RentalOrderResponse>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ApiResponse.ok("Lấy lịch sử đơn thuê của khách hàng thành công", rentalOrderService.getOrdersByCustomer(customerId));
    }
}
