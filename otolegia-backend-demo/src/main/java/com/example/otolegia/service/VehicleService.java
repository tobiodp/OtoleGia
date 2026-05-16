package com.example.otolegia.service;

import com.example.otolegia.dto.HomeResponse;
import com.example.otolegia.dto.VehicleResponse;
import com.example.otolegia.entity.AnhXe;
import com.example.otolegia.entity.ChuSoHuuXe;
import com.example.otolegia.entity.Xe;
import com.example.otolegia.exception.NotFoundException;
import com.example.otolegia.repository.XeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Xử lý nghiệp vụ tìm xe, xem xe, dữ liệu trang chủ.
 */
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final XeRepository xeRepository;

    /**
     * Trang chủ demo: trả một vài xe nổi bật đang AVAILABLE.
     */
    @Transactional(readOnly = true)
    public HomeResponse getHomeData() {
        List<VehicleResponse> xeNoiBat = searchVehicles(null, null, null, null, null, null)
                .stream()
                .limit(4)
                .toList();

        return new HomeResponse(
                "OTOLEGIA",
                "Dịch vụ thuê xe cao cấp, nhanh chóng và dễ theo dõi đơn thuê.",
                xeNoiBat
        );
    }

    /**
     * Tìm xe theo bộ lọc từ frontend.
     */
    @Transactional(readOnly = true)
    public List<VehicleResponse> searchVehicles(
            String keyword,
            String hangXe,
            String loaiXe,
            Integer soCho,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {
        return xeRepository.searchAvailableVehicles(keyword, hangXe, loaiXe, soCho, minPrice, maxPrice)
                .stream()
                .map(this::toVehicleResponse)
                .toList();
    }

    /**
     * Xem chi tiết một xe.
     */
    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(Long id) {
        Xe xe = xeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy xe có id = " + id));
        return toVehicleResponse(xe);
    }

    private VehicleResponse toVehicleResponse(Xe xe) {
        String anhChinh = xe.getAnhXeList()
                .stream()
                .filter(a -> Boolean.TRUE.equals(a.getAnhChinh()))
                .findFirst()
                .map(AnhXe::getDuongDanAnh)
                .orElse(null);

        ChuSoHuuXe chuSoHuu = xe.getChuSoHuu();

        return new VehicleResponse(
                xe.getId(),
                xe.getMaXe(),
                xe.getTenXe(),
                xe.getHangXe(),
                xe.getLoaiXe(),
                xe.getNamSanXuat(),
                xe.getBienSo(),
                xe.getSoCho(),
                xe.getNhienLieu(),
                xe.getHopSo(),
                xe.getGiaThueNgay(),
                xe.getMoTa(),
                xe.getTrangThai(),
                anhChinh,
                chuSoHuu.getLoaiChuSoHuu(),
                chuSoHuu.getTenChuSoHuu()
        );
    }
}
