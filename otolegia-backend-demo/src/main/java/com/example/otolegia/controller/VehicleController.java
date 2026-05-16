package com.example.otolegia.controller;

import com.example.otolegia.dto.ApiResponse;
import com.example.otolegia.dto.VehicleResponse;
import com.example.otolegia.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * API tìm xe và xem chi tiết xe.
 */
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Ví dụ:
     * GET /api/vehicles?keyword=Toyota
     * GET /api/vehicles?hangXe=Hyundai&soCho=5&minPrice=500000&maxPrice=1500000
     */
    @GetMapping
    public ApiResponse<List<VehicleResponse>> searchVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String hangXe,
            @RequestParam(required = false) String loaiXe,
            @RequestParam(required = false) Integer soCho,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        List<VehicleResponse> data = vehicleService.searchVehicles(keyword, hangXe, loaiXe, soCho, minPrice, maxPrice);
        return ApiResponse.ok("Tìm xe thành công", data);
    }

    @GetMapping("/{id}")
    public ApiResponse<VehicleResponse> getVehicleById(@PathVariable Long id) {
        return ApiResponse.ok("Lấy chi tiết xe thành công", vehicleService.getVehicleById(id));
    }
}
