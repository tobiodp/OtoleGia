package com.example.otolegia.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả thông tin thanh toán.
 */
public record PaymentResponse(
        Long id,
        String maThanhToan,
        Long rentalOrderId,
        String maDonThue,
        BigDecimal soTien,
        String phuongThuc,
        String maGiaoDich,
        String trangThai,
        LocalDateTime ngayThanhToan
) {
}
