package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Bảng doi_tac: đối tác đã được admin duyệt.
 */
@Entity
@Table(name = "doi_tac")
@Getter
@Setter
@NoArgsConstructor
public class DoiTac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_doi_tac", nullable = false, unique = true, length = 20)
    private String maDoiTac;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id", nullable = false, unique = true)
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

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai;

    @Column(name = "ngay_duyet")
    private LocalDateTime ngayDuyet;
}
