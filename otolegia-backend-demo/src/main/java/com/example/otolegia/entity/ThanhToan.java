package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Bảng thanh_toan: thanh toán cho một đơn thuê.
 */
@Entity
@Table(name = "thanh_toan")
@Getter
@Setter
@NoArgsConstructor
public class ThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_thanh_toan", nullable = false, unique = true, length = 20)
    private String maThanhToan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_thue_id", nullable = false, unique = true)
    private DonThue donThue;

    @Column(name = "so_tien", nullable = false, precision = 12, scale = 2)
    private BigDecimal soTien;

    @Column(name = "phuong_thuc", nullable = false, length = 50)
    private String phuongThuc; // CASH, BANK_TRANSFER, MOMO, CARD

    @Column(name = "ma_giao_dich", length = 100)
    private String maGiaoDich;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai; // PENDING, SUCCESS, FAILED

    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;
}
