package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Bảng nguoi_dung: lưu khách hàng, đối tác, admin.
 */
@Entity
@Table(name = "nguoi_dung")
@Getter
@Setter
@NoArgsConstructor
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_nguoi_dung", nullable = false, unique = true, length = 20)
    private String maNguoiDung;

    @Column(name = "ho_ten", nullable = false, length = 150)
    private String hoTen;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "vai_tro", nullable = false, length = 30)
    private String vaiTro; // CUSTOMER, PARTNER, ADMIN

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai; // ACTIVE, LOCKED

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}
