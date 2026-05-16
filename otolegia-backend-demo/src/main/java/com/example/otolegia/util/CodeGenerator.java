package com.example.otolegia.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Sinh mã hiển thị như DT2026051310010199.
 * Lưu ý: mã này đủ dùng cho demo. Khi làm thật nên dùng cơ chế chắc chắn hơn.
 */
public class CodeGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private CodeGenerator() {
    }

    public static String generate(String prefix) {
        int random = ThreadLocalRandom.current().nextInt(10, 100);
        return prefix + LocalDateTime.now().format(FORMATTER) + random;
    }
}
