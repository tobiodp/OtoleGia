package com.example.otolegia.service;

import com.example.otolegia.dto.PartnerRegistrationRequest;
import com.example.otolegia.dto.PartnerRegistrationResponse;
import com.example.otolegia.entity.DangKyDoiTac;
import com.example.otolegia.entity.NguoiDung;
import com.example.otolegia.exception.NotFoundException;
import com.example.otolegia.repository.DangKyDoiTacRepository;
import com.example.otolegia.repository.NguoiDungRepository;
import com.example.otolegia.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Phần đăng ký đối tác để mô hình DB đầy đủ hơn.
 * Flow thuê xe chính không bắt buộc dùng phần này.
 */
@Service
@RequiredArgsConstructor
public class PartnerRegistrationService {

    private final DangKyDoiTacRepository dangKyDoiTacRepository;
    private final NguoiDungRepository nguoiDungRepository;

    @Transactional
    public PartnerRegistrationResponse createRegistration(PartnerRegistrationRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng id = " + request.userId()));

        DangKyDoiTac registration = new DangKyDoiTac();
        registration.setMaDangKy(CodeGenerator.generate("DK"));
        registration.setNguoiDung(nguoiDung);
        registration.setTenDoiTac(request.tenDoiTac());
        registration.setLoaiDoiTac(request.loaiDoiTac());
        registration.setSoDienThoai(request.soDienThoai());
        registration.setEmail(request.email());
        registration.setDiaChi(request.diaChi());
        registration.setMaSoThue(request.maSoThue());
        registration.setGhiChu(request.ghiChu());
        registration.setTrangThai("PENDING");
        registration.setNgayTao(LocalDateTime.now());

        return toResponse(dangKyDoiTacRepository.save(registration));
    }

    @Transactional(readOnly = true)
    public PartnerRegistrationResponse getRegistration(Long id) {
        DangKyDoiTac registration = dangKyDoiTacRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hồ sơ đăng ký đối tác id = " + id));
        return toResponse(registration);
    }

    private PartnerRegistrationResponse toResponse(DangKyDoiTac registration) {
        return new PartnerRegistrationResponse(
                registration.getId(),
                registration.getMaDangKy(),
                registration.getNguoiDung().getId(),
                registration.getNguoiDung().getHoTen(),
                registration.getTenDoiTac(),
                registration.getLoaiDoiTac(),
                registration.getSoDienThoai(),
                registration.getEmail(),
                registration.getDiaChi(),
                registration.getMaSoThue(),
                registration.getTrangThai(),
                registration.getLyDoTuChoi(),
                registration.getNgayTao(),
                registration.getNgayDuyet()
        );
    }
}
