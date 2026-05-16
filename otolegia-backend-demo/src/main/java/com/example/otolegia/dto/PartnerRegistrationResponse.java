package com.example.otolegia.dto;

import java.time.LocalDateTime;

public record PartnerRegistrationResponse(
        Long id,
        String maDangKy,
        Long userId,
        String tenNguoiDung,
        String tenDoiTac,
        String loaiDoiTac,
        String soDienThoai,
        String email,
        String diaChi,
        String maSoThue,
        String trangThai,
        String lyDoTuChoi,
        LocalDateTime ngayTao,
        LocalDateTime ngayDuyet
) {
}
