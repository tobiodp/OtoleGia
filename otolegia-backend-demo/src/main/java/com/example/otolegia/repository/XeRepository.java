package com.example.otolegia.repository;

import com.example.otolegia.entity.Xe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface XeRepository extends JpaRepository<Xe, Long> {

    /**
     * API tìm xe ở trang chủ.
     * Chỉ lấy xe AVAILABLE, có thể lọc theo từ khóa/hãng/loại/số chỗ/giá.
     */
    @Query("""
            SELECT x FROM Xe x
            WHERE x.trangThai = 'AVAILABLE'
              AND (:keyword IS NULL OR :keyword = '' OR
                   LOWER(x.tenXe) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(x.hangXe) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                   LOWER(x.loaiXe) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:hangXe IS NULL OR :hangXe = '' OR LOWER(x.hangXe) LIKE LOWER(CONCAT('%', :hangXe, '%')))
              AND (:loaiXe IS NULL OR :loaiXe = '' OR LOWER(x.loaiXe) LIKE LOWER(CONCAT('%', :loaiXe, '%')))
              AND (:soCho IS NULL OR x.soCho = :soCho)
              AND (:minPrice IS NULL OR x.giaThueNgay >= :minPrice)
              AND (:maxPrice IS NULL OR x.giaThueNgay <= :maxPrice)
            ORDER BY x.giaThueNgay ASC
            """)
    List<Xe> searchAvailableVehicles(
            @Param("keyword") String keyword,
            @Param("hangXe") String hangXe,
            @Param("loaiXe") String loaiXe,
            @Param("soCho") Integer soCho,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
}
