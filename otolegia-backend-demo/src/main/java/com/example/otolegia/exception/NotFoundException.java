package com.example.otolegia.exception;

/**
 * Lỗi khi không tìm thấy dữ liệu trong DB.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
