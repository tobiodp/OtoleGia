package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Bảng chu_so_huu_xe: đại diện cho công ty hoặc đối tác sở hữu xe.
 */
@Entity
@Table(name = "chu_so_huu_xe")
@Getter
@Setter
@NoArgsConstructor
public class ChuSoHuuXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_chu_so_huu", nullable = false, unique = true, length = 20)
    private String maChuSoHuu;

    @Column(name = "loai_chu_so_huu", nullable = false, length = 30)
    private String loaiChuSoHuu; // COMPANY, PARTNER

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doi_tac_id")
    private DoiTac doiTac;

    @Column(name = "ten_chu_so_huu", nullable = false, length = 150)
    private String tenChuSoHuu;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
}
