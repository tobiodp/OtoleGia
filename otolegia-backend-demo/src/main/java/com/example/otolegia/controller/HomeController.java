package com.example.otolegia.controller;

import com.example.otolegia.dto.ApiResponse;
import com.example.otolegia.dto.HomeResponse;
import com.example.otolegia.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API trang chủ.
 */
@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final VehicleService vehicleService;

    @GetMapping
    public ApiResponse<HomeResponse> home() {
        return ApiResponse.ok("Lấy dữ liệu trang chủ thành công", vehicleService.getHomeData());
    }
}
