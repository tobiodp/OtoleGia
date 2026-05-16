package com.example.otolegia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request đăng ký đối tác. Phần này để mở rộng, không bắt buộc trong flow thuê xe demo.
 */
public record PartnerRegistrationRequest(
        @NotNull(message = "userId không được để trống")
        Long userId,

        @NotBlank(message = "tenDoiTac không được để trống")
        String tenDoiTac,

        String loaiDoiTac,
        String soDienThoai,
        String email,
        String diaChi,
        String maSoThue,
        String ghiChu
) {
}
