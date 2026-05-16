package com.example.otolegia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request thanh toán demo.
 */
public record PaymentRequest(
        @NotNull(message = "rentalOrderId không được để trống")
        Long rentalOrderId,

        @NotBlank(message = "phuongThuc không được để trống")
        String phuongThuc,

        String maGiaoDich
) {
}
