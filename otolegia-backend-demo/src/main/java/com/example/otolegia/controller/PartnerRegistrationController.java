package com.example.otolegia.controller;

import com.example.otolegia.dto.ApiResponse;
import com.example.otolegia.dto.PartnerRegistrationRequest;
import com.example.otolegia.dto.PartnerRegistrationResponse;
import com.example.otolegia.service.PartnerRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API đăng ký đối tác, thêm để đúng nghiệp vụ app.
 */
@RestController
@RequestMapping("/api/partner-registrations")
@RequiredArgsConstructor
public class PartnerRegistrationController {

    private final PartnerRegistrationService partnerRegistrationService;

    @PostMapping
    public ApiResponse<PartnerRegistrationResponse> createRegistration(
            @Valid @RequestBody PartnerRegistrationRequest request
    ) {
        return ApiResponse.ok("Gửi hồ sơ đăng ký đối tác thành công", partnerRegistrationService.createRegistration(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PartnerRegistrationResponse> getRegistration(@PathVariable Long id) {
        return ApiResponse.ok("Lấy hồ sơ đăng ký đối tác thành công", partnerRegistrationService.getRegistration(id));
    }
}
