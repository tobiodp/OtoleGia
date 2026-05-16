package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Bảng xe: thông tin xe cho thuê.
 */
@Entity
@Table(name = "xe")
@Getter
@Setter
@NoArgsConstructor
public class Xe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_xe", nullable = false, unique = true, length = 20)
    private String maXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chu_so_huu_id", nullable = false)
    private ChuSoHuuXe chuSoHuu;

    @Column(name = "ten_xe", nullable = false, length = 150)
    private String tenXe;

    @Column(name = "hang_xe", length = 100)
    private String hangXe;

    @Column(name = "loai_xe", length = 100)
    private String loaiXe;

    @Column(name = "nam_san_xuat")
    private Integer namSanXuat;

    @Column(name = "bien_so", unique = true, length = 30)
    private String bienSo;

    @Column(name = "so_cho")
    private Integer soCho;

    @Column(name = "nhien_lieu", length = 50)
    private String nhienLieu;

    @Column(name = "hop_so", length = 50)
    private String hopSo;

    @Column(name = "gia_thue_ngay", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaThueNgay;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai; // AVAILABLE, RENTED, MAINTENANCE, HIDDEN

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @OneToMany(mappedBy = "xe", fetch = FetchType.LAZY)
    private List<AnhXe> anhXeList = new ArrayList<>();
}
