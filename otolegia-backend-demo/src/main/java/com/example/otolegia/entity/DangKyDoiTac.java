package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Bảng dang_ky_doi_tac: hồ sơ người dùng gửi để xin làm đối tác.
 */
@Entity
@Table(name = "dang_ky_doi_tac")
@Getter
@Setter
@NoArgsConstructor
public class DangKyDoiTac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_dang_ky", nullable = false, unique = true, length = 20)
    private String maDangKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "ten_doi_tac", nullable = false, length = 150)
    private String tenDoiTac;

    @Column(name = "loai_doi_tac", length = 50)
    private String loaiDoiTac;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ma_so_thue", length = 50)
    private String maSoThue;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai; // PENDING, APPROVED, REJECTED

    @Column(name = "ly_do_tu_choi", columnDefinition = "TEXT")
    private String lyDoTuChoi;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_duyet")
    private LocalDateTime ngayDuyet;
}
