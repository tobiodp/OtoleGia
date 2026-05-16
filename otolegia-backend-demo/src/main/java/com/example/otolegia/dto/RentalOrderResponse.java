package com.example.otolegia.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả thông tin đơn thuê cho frontend theo dõi đơn.
 */
public record RentalOrderResponse(
        Long id,
        String maDonThue,
        Long customerId,
        String tenKhachHang,
        Long vehicleId,
        String tenXe,
        String hangXe,
        LocalDateTime thoiGianNhan,
        LocalDateTime thoiGianTra,
        String diaDiemNhan,
        String diaDiemTra,
        BigDecimal donGiaNgay,
        Integer soNgayThue,
        BigDecimal tongTien,
        String trangThai,
        String trangThaiThanhToan,
        LocalDateTime ngayTao
) {
}
