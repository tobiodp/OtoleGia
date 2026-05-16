package com.example.otolegia.dto;

import java.util.List;

/**
 * Dữ liệu cho trang chủ demo.
 */
public record HomeResponse(
        String appName,
        String slogan,
        List<VehicleResponse> xeNoiBat
) {
}
