package com.example.otolegia.controller;

import com.example.otolegia.dto.ApiResponse;
import com.example.otolegia.dto.PaymentRequest;
import com.example.otolegia.dto.PaymentResponse;
import com.example.otolegia.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API thanh toán demo.
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentResponse> pay(@Valid @RequestBody PaymentRequest request) {
        return ApiResponse.ok("Thanh toán thành công", paymentService.pay(request));
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<PaymentResponse> getPaymentByOrder(@PathVariable Long orderId) {
        return ApiResponse.ok("Lấy thông tin thanh toán thành công", paymentService.getPaymentByOrderId(orderId));
    }
}
