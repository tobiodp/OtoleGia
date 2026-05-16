package com.example.otolegia.dto;

import java.math.BigDecimal;

/**
 * DTO trả dữ liệu xe ra frontend, tránh trả trực tiếp Entity.
 */
public record VehicleResponse(
        Long id,
        String maXe,
        String tenXe,
        String hangXe,
        String loaiXe,
        Integer namSanXuat,
        String bienSo,
        Integer soCho,
        String nhienLieu,
        String hopSo,
        BigDecimal giaThueNgay,
        String moTa,
        String trangThai,
        String anhChinh,
        String loaiChuSoHuu,
        String tenChuSoHuu
) {
}
