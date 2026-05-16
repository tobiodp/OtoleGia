package com.example.otolegia.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Request đặt xe.
 */
public record CreateRentalOrderRequest(
        @NotNull(message = "customerId không được để trống")
        Long customerId,

        @NotNull(message = "vehicleId không được để trống")
        Long vehicleId,

        @NotNull(message = "thoiGianNhan không được để trống")
        @Future(message = "thoiGianNhan phải là thời gian trong tương lai")
        LocalDateTime thoiGianNhan,

        @NotNull(message = "thoiGianTra không được để trống")
        @Future(message = "thoiGianTra phải là thời gian trong tương lai")
        LocalDateTime thoiGianTra,

        String diaDiemNhan,
        String diaDiemTra
) {
}
