-- =====================================================
-- DATABASE DEMO CHO HỆ THỐNG THUÊ XE OTOLEGIA
-- Flow chính: Trang chủ -> Tìm xe -> Đặt xe -> Thanh toán -> Theo dõi đơn thuê
-- MySQL 8+
-- =====================================================

DROP DATABASE IF EXISTS otolegia_demo;
CREATE DATABASE otolegia_demo
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE otolegia_demo;

-- =========================
-- 1. NGƯỜI DÙNG
-- =========================
CREATE TABLE nguoi_dung (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_nguoi_dung VARCHAR(20) UNIQUE NOT NULL,
    ho_ten VARCHAR(150) NOT NULL,
    so_dien_thoai VARCHAR(20),
    email VARCHAR(150) UNIQUE,
    mat_khau VARCHAR(255) NOT NULL,
    vai_tro VARCHAR(30) NOT NULL DEFAULT 'CUSTOMER',
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- 2. HỒ SƠ ĐĂNG KÝ ĐỐI TÁC
-- =========================
CREATE TABLE dang_ky_doi_tac (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_dang_ky VARCHAR(20) UNIQUE NOT NULL,
    nguoi_dung_id BIGINT NOT NULL,
    ten_doi_tac VARCHAR(150) NOT NULL,
    loai_doi_tac VARCHAR(50),
    so_dien_thoai VARCHAR(20),
    email VARCHAR(150),
    dia_chi VARCHAR(255),
    ma_so_thue VARCHAR(50),
    ghi_chu TEXT,
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    ly_do_tu_choi TEXT,
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ngay_duyet DATETIME,
    CONSTRAINT fk_dang_ky_doi_tac_nguoi_dung
        FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id)
);

-- =========================
-- 3. ĐỐI TÁC ĐÃ ĐƯỢC DUYỆT
-- =========================
CREATE TABLE doi_tac (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_doi_tac VARCHAR(20) UNIQUE NOT NULL,
    nguoi_dung_id BIGINT UNIQUE NOT NULL,
    ten_doi_tac VARCHAR(150) NOT NULL,
    loai_doi_tac VARCHAR(50),
    so_dien_thoai VARCHAR(20),
    email VARCHAR(150),
    dia_chi VARCHAR(255),
    ma_so_thue VARCHAR(50),
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    ngay_duyet DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_doi_tac_nguoi_dung
        FOREIGN KEY (nguoi_dung_id) REFERENCES nguoi_dung(id)
);

-- =========================
-- 4. CHỦ SỞ HỮU XE
-- Công ty và đối tác đều là chủ sở hữu xe
-- =========================
CREATE TABLE chu_so_huu_xe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_chu_so_huu VARCHAR(20) UNIQUE NOT NULL,
    loai_chu_so_huu VARCHAR(30) NOT NULL,
    doi_tac_id BIGINT NULL,
    ten_chu_so_huu VARCHAR(150) NOT NULL,
    so_dien_thoai VARCHAR(20),
    email VARCHAR(150),
    dia_chi VARCHAR(255),
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_chu_so_huu_doi_tac
        FOREIGN KEY (doi_tac_id) REFERENCES doi_tac(id),
    CONSTRAINT chk_chu_so_huu_xe CHECK (
        (loai_chu_so_huu = 'COMPANY' AND doi_tac_id IS NULL)
        OR
        (loai_chu_so_huu = 'PARTNER' AND doi_tac_id IS NOT NULL)
    )
);

-- =========================
-- 5. XE
-- =========================
CREATE TABLE xe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_xe VARCHAR(20) UNIQUE NOT NULL,
    chu_so_huu_id BIGINT NOT NULL,
    ten_xe VARCHAR(150) NOT NULL,
    hang_xe VARCHAR(100),
    loai_xe VARCHAR(100),
    nam_san_xuat INT,
    bien_so VARCHAR(30) UNIQUE,
    so_cho INT,
    nhien_lieu VARCHAR(50),
    hop_so VARCHAR(50),
    gia_thue_ngay DECIMAL(12,2) NOT NULL,
    mo_ta TEXT,
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_xe_chu_so_huu
        FOREIGN KEY (chu_so_huu_id) REFERENCES chu_so_huu_xe(id)
);

-- =========================
-- 6. ẢNH XE
-- =========================
CREATE TABLE anh_xe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_anh VARCHAR(20) UNIQUE NOT NULL,
    xe_id BIGINT NOT NULL,
    duong_dan_anh VARCHAR(255) NOT NULL,
    anh_chinh BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_anh_xe_xe
        FOREIGN KEY (xe_id) REFERENCES xe(id) ON DELETE CASCADE
);

-- =========================
-- 7. ĐƠN THUÊ
-- =========================
CREATE TABLE don_thue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_don_thue VARCHAR(20) UNIQUE NOT NULL,
    khach_hang_id BIGINT NOT NULL,
    xe_id BIGINT NOT NULL,
    thoi_gian_nhan DATETIME NOT NULL,
    thoi_gian_tra DATETIME NOT NULL,
    dia_diem_nhan VARCHAR(255),
    dia_diem_tra VARCHAR(255),
    don_gia_ngay DECIMAL(12,2) NOT NULL,
    so_ngay_thue INT NOT NULL,
    tong_tien DECIMAL(12,2) NOT NULL,
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_don_thue_khach_hang
        FOREIGN KEY (khach_hang_id) REFERENCES nguoi_dung(id),
    CONSTRAINT fk_don_thue_xe
        FOREIGN KEY (xe_id) REFERENCES xe(id),
    CONSTRAINT chk_thoi_gian_thue CHECK (thoi_gian_tra > thoi_gian_nhan)
);

-- =========================
-- 8. THANH TOÁN
-- =========================
CREATE TABLE thanh_toan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_thanh_toan VARCHAR(20) UNIQUE NOT NULL,
    don_thue_id BIGINT NOT NULL UNIQUE,
    so_tien DECIMAL(12,2) NOT NULL,
    phuong_thuc VARCHAR(50) NOT NULL,
    ma_giao_dich VARCHAR(100),
    trang_thai VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    ngay_thanh_toan DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_thanh_toan_don_thue
        FOREIGN KEY (don_thue_id) REFERENCES don_thue(id)
);

-- =========================
-- 9. ĐÁNH GIÁ
-- =========================
CREATE TABLE danh_gia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_danh_gia VARCHAR(20) UNIQUE NOT NULL,
    don_thue_id BIGINT NOT NULL UNIQUE,
    khach_hang_id BIGINT NOT NULL,
    xe_id BIGINT NOT NULL,
    so_sao INT NOT NULL,
    noi_dung TEXT,
    trang_thai VARCHAR(30) DEFAULT 'VISIBLE',
    ngay_danh_gia DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_danh_gia_don_thue
        FOREIGN KEY (don_thue_id) REFERENCES don_thue(id),
    CONSTRAINT fk_danh_gia_khach_hang
        FOREIGN KEY (khach_hang_id) REFERENCES nguoi_dung(id),
    CONSTRAINT fk_danh_gia_xe
        FOREIGN KEY (xe_id) REFERENCES xe(id),
    CONSTRAINT chk_so_sao CHECK (so_sao BETWEEN 1 AND 5)
);

-- =====================================================
-- DỮ LIỆU MẪU
-- =====================================================
INSERT INTO nguoi_dung (ma_nguoi_dung, ho_ten, so_dien_thoai, email, mat_khau, vai_tro, trang_thai)
VALUES
('ND001', 'Nguyễn Văn Khách', '0901111222', 'khach@example.com', '123456', 'CUSTOMER', 'ACTIVE'),
('ND002', 'Trần Văn Đối Tác', '0902222333', 'doitac@example.com', '123456', 'PARTNER', 'ACTIVE'),
('ND003', 'Admin OTOLEGIA', '0903333444', 'admin@example.com', '123456', 'ADMIN', 'ACTIVE');

INSERT INTO dang_ky_doi_tac (
    ma_dang_ky, nguoi_dung_id, ten_doi_tac, loai_doi_tac,
    so_dien_thoai, email, dia_chi, ma_so_thue, trang_thai, ngay_duyet
)
VALUES
('DK001', 2, 'Công ty Xe Sang Legia', 'DOANH_NGHIEP', '0902222333', 'doitac@example.com', 'TP.HCM', '0312345678', 'APPROVED', NOW());

INSERT INTO doi_tac (
    ma_doi_tac, nguoi_dung_id, ten_doi_tac, loai_doi_tac,
    so_dien_thoai, email, dia_chi, ma_so_thue, trang_thai
)
VALUES
('DT001', 2, 'Công ty Xe Sang Legia', 'DOANH_NGHIEP', '0902222333', 'doitac@example.com', 'TP.HCM', '0312345678', 'ACTIVE');

INSERT INTO chu_so_huu_xe (
    ma_chu_so_huu, loai_chu_so_huu, doi_tac_id,
    ten_chu_so_huu, so_dien_thoai, email, dia_chi, trang_thai
)
VALUES
('CSH001', 'COMPANY', NULL, 'Công ty OTOLEGIA', '0900000000', 'company@otolegia.vn', 'TP.HCM', 'ACTIVE'),
('CSH002', 'PARTNER', 1, 'Công ty Xe Sang Legia', '0902222333', 'doitac@example.com', 'TP.HCM', 'ACTIVE');

INSERT INTO xe (
    ma_xe, chu_so_huu_id, ten_xe, hang_xe, loai_xe,
    nam_san_xuat, bien_so, so_cho, nhien_lieu, hop_so,
    gia_thue_ngay, mo_ta, trang_thai
)
VALUES
('XE001', 1, 'Hyundai Accent', 'Hyundai', 'Sedan', 2022, '51A-12345', 5, 'Xăng', 'Tự động', 850000, 'Xe công ty, phù hợp đi trong thành phố.', 'AVAILABLE'),
('XE002', 1, 'VinFast VF3', 'VinFast', 'SUV điện', 2024, '51E-88888', 4, 'Điện', 'Tự động', 950000, 'Xe điện nhỏ gọn, phù hợp di chuyển nội thành.', 'AVAILABLE'),
('XE003', 2, 'Mercedes-Benz S-Class', 'Mercedes-Benz', 'Sedan cao cấp', 2024, '51G-99999', 5, 'Xăng', 'Tự động', 3500000, 'Xe đối tác, phục vụ khách VIP và sự kiện.', 'AVAILABLE'),
('XE004', 2, 'Toyota Camry', 'Toyota', 'Sedan', 2023, '51F-22222', 5, 'Xăng', 'Tự động', 1200000, 'Xe đối tác, phù hợp đi công tác và gia đình.', 'AVAILABLE'),
('XE005', 1, 'Kia Carnival', 'Kia', 'MPV', 2023, '51K-77777', 7, 'Dầu', 'Tự động', 1800000, 'Xe công ty, rộng rãi cho gia đình hoặc nhóm khách.', 'MAINTENANCE');

INSERT INTO anh_xe (ma_anh, xe_id, duong_dan_anh, anh_chinh)
VALUES
('AX001', 1, '/images/hyundai-accent.jpg', TRUE),
('AX002', 2, '/images/vinfast-vf3.jpg', TRUE),
('AX003', 3, '/images/mercedes-s-class.jpg', TRUE),
('AX004', 4, '/images/toyota-camry.jpg', TRUE),
('AX005', 5, '/images/kia-carnival.jpg', TRUE);
