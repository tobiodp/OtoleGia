package com.example.otolegia.exception;

/**
 * Lỗi nghiệp vụ hoặc dữ liệu request không hợp lệ.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
