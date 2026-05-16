package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Bảng danh_gia: đánh giá sau khi thuê xe.
 * Demo chính chưa dùng nhiều, nhưng vẫn khai báo để khớp DB.
 */
@Entity
@Table(name = "danh_gia")
@Getter
@Setter
@NoArgsConstructor
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_danh_gia", nullable = false, unique = true, length = 20)
    private String maDanhGia;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_thue_id", nullable = false, unique = true)
    private DonThue donThue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private NguoiDung khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xe_id", nullable = false)
    private Xe xe;

    @Column(name = "so_sao", nullable = false)
    private Integer soSao;

    @Column(name = "noi_dung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "trang_thai", length = 30)
    private String trangThai;

    @Column(name = "ngay_danh_gia")
    private LocalDateTime ngayDanhGia;
}
