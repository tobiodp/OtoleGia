package com.example.otolegia.service;

import com.example.otolegia.dto.PaymentRequest;
import com.example.otolegia.dto.PaymentResponse;
import com.example.otolegia.entity.DonThue;
import com.example.otolegia.entity.ThanhToan;
import com.example.otolegia.entity.Xe;
import com.example.otolegia.exception.BadRequestException;
import com.example.otolegia.exception.NotFoundException;
import com.example.otolegia.repository.DonThueRepository;
import com.example.otolegia.repository.ThanhToanRepository;
import com.example.otolegia.repository.XeRepository;
import com.example.otolegia.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Xử lý thanh toán demo.
 * Khi thanh toán thành công: đơn thuê chuyển PAID, xe chuyển RENTED.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ThanhToanRepository thanhToanRepository;
    private final DonThueRepository donThueRepository;
    private final XeRepository xeRepository;

    /**
     * Thanh toán demo: mặc định thanh toán thành công.
     */
    @Transactional
    public PaymentResponse pay(PaymentRequest request) {
        DonThue donThue = donThueRepository.findById(request.rentalOrderId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn thuê id = " + request.rentalOrderId()));

        if (thanhToanRepository.existsByDonThueId(donThue.getId())) {
            throw new BadRequestException("Đơn thuê này đã có thanh toán, không thể thanh toán lại");
        }

        if (!"PENDING".equalsIgnoreCase(donThue.getTrangThai())) {
            throw new BadRequestException("Chỉ có thể thanh toán đơn đang PENDING. Trạng thái hiện tại: " + donThue.getTrangThai());
        }

        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setMaThanhToan(CodeGenerator.generate("TT"));
        thanhToan.setDonThue(donThue);
        thanhToan.setSoTien(donThue.getTongTien());
        thanhToan.setPhuongThuc(request.phuongThuc());
        thanhToan.setMaGiaoDich(request.maGiaoDich());
        thanhToan.setTrangThai("SUCCESS");
        thanhToan.setNgayThanhToan(LocalDateTime.now());

        // Cập nhật đơn và xe sau thanh toán.
        donThue.setTrangThai("PAID");
        Xe xe = donThue.getXe();
        xe.setTrangThai("RENTED");

        ThanhToan saved = thanhToanRepository.save(thanhToan);
        donThueRepository.save(donThue);
        xeRepository.save(xe);

        return toPaymentResponse(saved);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        ThanhToan thanhToan = thanhToanRepository.findByDonThueId(orderId)
                .orElseThrow(() -> new NotFoundException("Đơn thuê này chưa có thông tin thanh toán"));
        return toPaymentResponse(thanhToan);
    }

    private PaymentResponse toPaymentResponse(ThanhToan thanhToan) {
        return new PaymentResponse(
                thanhToan.getId(),
                thanhToan.getMaThanhToan(),
                thanhToan.getDonThue().getId(),
                thanhToan.getDonThue().getMaDonThue(),
                thanhToan.getSoTien(),
                thanhToan.getPhuongThuc(),
                thanhToan.getMaGiaoDich(),
                thanhToan.getTrangThai(),
                thanhToan.getNgayThanhToan()
        );
    }
}
