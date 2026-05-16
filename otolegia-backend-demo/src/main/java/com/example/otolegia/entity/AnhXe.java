package com.example.otolegia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bảng anh_xe: lưu đường dẫn ảnh của xe.
 */
@Entity
@Table(name = "anh_xe")
@Getter
@Setter
@NoArgsConstructor
public class AnhXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_anh", nullable = false, unique = true, length = 20)
    private String maAnh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xe_id", nullable = false)
    private Xe xe;

    @Column(name = "duong_dan_anh", nullable = false)
    private String duongDanAnh;

    @Column(name = "anh_chinh")
    private Boolean anhChinh;
}
