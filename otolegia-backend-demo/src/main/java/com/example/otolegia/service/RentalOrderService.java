package com.example.otolegia.service;

import com.example.otolegia.dto.CreateRentalOrderRequest;
import com.example.otolegia.dto.RentalOrderResponse;
import com.example.otolegia.entity.DonThue;
import com.example.otolegia.entity.NguoiDung;
import com.example.otolegia.entity.ThanhToan;
import com.example.otolegia.entity.Xe;
import com.example.otolegia.exception.BadRequestException;
import com.example.otolegia.exception.NotFoundException;
import com.example.otolegia.repository.DonThueRepository;
import com.example.otolegia.repository.NguoiDungRepository;
import com.example.otolegia.repository.ThanhToanRepository;
import com.example.otolegia.repository.XeRepository;
import com.example.otolegia.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Xử lý nghiệp vụ đặt xe và theo dõi đơn thuê.
 */
@Service
@RequiredArgsConstructor
public class RentalOrderService {

    private final DonThueRepository donThueRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final XeRepository xeRepository;
    private final ThanhToanRepository thanhToanRepository;

    /**
     * Đặt xe: tạo đơn thuê trạng thái PENDING.
     * Demo này chưa kiểm tra trùng lịch theo ngày, chỉ kiểm tra xe đang AVAILABLE.
     */
    @Transactional
    public RentalOrderResponse createOrder(CreateRentalOrderRequest request) {
        if (!request.thoiGianTra().isAfter(request.thoiGianNhan())) {
            throw new BadRequestException("Thời gian trả xe phải sau thời gian nhận xe");
        }

        NguoiDung khachHang = nguoiDungRepository.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng id = " + request.customerId()));

        if (!"ACTIVE".equalsIgnoreCase(khachHang.getTrangThai())) {
            throw new BadRequestException("Tài khoản khách hàng không hoạt động");
        }

        Xe xe = xeRepository.findById(request.vehicleId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy xe id = " + request.vehicleId()));

        if (!"AVAILABLE".equalsIgnoreCase(xe.getTrangThai())) {
            throw new BadRequestException("Xe hiện không sẵn sàng để thuê. Trạng thái hiện tại: " + xe.getTrangThai());
        }

        int soNgayThue = calculateRentalDays(request.thoiGianNhan(), request.thoiGianTra());
        BigDecimal tongTien = xe.getGiaThueNgay().multiply(BigDecimal.valueOf(soNgayThue));

        DonThue donThue = new DonThue();
        donThue.setMaDonThue(CodeGenerator.generate("DT"));
        donThue.setKhachHang(khachHang);
        donThue.setXe(xe);
        donThue.setThoiGianNhan(request.thoiGianNhan());
        donThue.setThoiGianTra(request.thoiGianTra());
        donThue.setDiaDiemNhan(request.diaDiemNhan());
        donThue.setDiaDiemTra(request.diaDiemTra());
        donThue.setDonGiaNgay(xe.getGiaThueNgay());
        donThue.setSoNgayThue(soNgayThue);
        donThue.setTongTien(tongTien);
        donThue.setTrangThai("PENDING");
        donThue.setNgayTao(LocalDateTime.now());

        DonThue saved = donThueRepository.save(donThue);
        return toRentalOrderResponse(saved);
    }

    /**
     * Theo dõi chi tiết một đơn thuê.
     */
    @Transactional(readOnly = true)
    public RentalOrderResponse getOrderById(Long id) {
        DonThue donThue = donThueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn thuê id = " + id));
        return toRentalOrderResponse(donThue);
    }

    /**
     * Lấy lịch sử đơn thuê theo khách hàng.
     */
    @Transactional(readOnly = true)
    public List<RentalOrderResponse> getOrdersByCustomer(Long customerId) {
        if (!nguoiDungRepository.existsById(customerId)) {
            throw new NotFoundException("Không tìm thấy khách hàng id = " + customerId);
        }

        return donThueRepository.findByKhachHangIdOrderByNgayTaoDesc(customerId)
                .stream()
                .map(this::toRentalOrderResponse)
                .toList();
    }

    private int calculateRentalDays(LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        if (minutes <= 0) {
            throw new BadRequestException("Thời gian thuê không hợp lệ");
        }

        // Làm tròn lên theo ngày: thuê 25 giờ tính 2 ngày, thuê 30 phút vẫn tính 1 ngày.
        return Math.max(1, (int) Math.ceil(minutes / 1440.0));
    }

    private RentalOrderResponse toRentalOrderResponse(DonThue donThue) {
        String trangThaiThanhToan = thanhToanRepository.findByDonThueId(donThue.getId())
                .map(ThanhToan::getTrangThai)
                .orElse("CHUA_THANH_TOAN");

        return new RentalOrderResponse(
                donThue.getId(),
                donThue.getMaDonThue(),
                donThue.getKhachHang().getId(),
                donThue.getKhachHang().getHoTen(),
                donThue.getXe().getId(),
                donThue.getXe().getTenXe(),
                donThue.getXe().getHangXe(),
                donThue.getThoiGianNhan(),
                donThue.getThoiGianTra(),
                donThue.getDiaDiemNhan(),
                donThue.getDiaDiemTra(),
                donThue.getDonGiaNgay(),
                donThue.getSoNgayThue(),
                donThue.getTongTien(),
                donThue.getTrangThai(),
                trangThaiThanhToan,
                donThue.getNgayTao()
        );
    }
}
