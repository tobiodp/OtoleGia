package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Bảng don_thue: đơn đặt thuê xe của khách hàng.
 */
@Entity
@Table(name = "don_thue")
@Getter
@Setter
@NoArgsConstructor
public class DonThue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_don_thue", nullable = false, unique = true, length = 20)
    private String maDonThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private NguoiDung khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xe_id", nullable = false)
    private Xe xe;

    @Column(name = "thoi_gian_nhan", nullable = false)
    private LocalDateTime thoiGianNhan;

    @Column(name = "thoi_gian_tra", nullable = false)
    private LocalDateTime thoiGianTra;

    @Column(name = "dia_diem_nhan")
    private String diaDiemNhan;

    @Column(name = "dia_diem_tra")
    private String diaDiemTra;

    @Column(name = "don_gia_ngay", nullable = false, precision = 12, scale = 2)
    private BigDecimal donGiaNgay;

    @Column(name = "so_ngay_thue", nullable = false)
    private Integer soNgayThue;

    @Column(name = "tong_tien", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai; // PENDING, PAID, RENTING, COMPLETED, CANCELLED

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}
