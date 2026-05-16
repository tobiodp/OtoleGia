# OTOLEGIA Backend Demo - Spring Boot + MySQL

Backend demo cho flow:

```text
Trang chủ -> Tìm xe -> Xem chi tiết xe -> Đặt xe -> Thanh toán -> Theo dõi đơn thuê
```

## 1. Công nghệ

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- MySQL
- Lombok

## 2. Cấu trúc thư mục

```text
src/main/java/com/example/otolegia
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
└── util
```

## 3. Tạo database

Mở MySQL Workbench và chạy file:

```text
database/otolegia_demo.sql
```

File này sẽ tạo database `otolegia_demo`, tạo bảng và thêm dữ liệu mẫu.

## 4. Cấu hình MySQL

Mở file:

```text
src/main/resources/application.properties
```

Sửa password theo máy của bạn:

```properties
spring.datasource.username=root
spring.datasource.password=123456
```

## 5. Chạy project bằng IntelliJ

1. Open folder `otolegia-backend-demo` bằng IntelliJ.
2. Chờ Maven tải dependency.
3. Bật annotation processing nếu Lombok báo lỗi:
   - File -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
   - Tick `Enable annotation processing`
4. Run file:

```text
OtolegiaBackendDemoApplication.java
```

Server chạy tại:

```text
http://localhost:8080
```

## 6. Danh sách API chính

### Trang chủ

```http
GET http://localhost:8080/api/home
```

### Tìm xe

```http
GET http://localhost:8080/api/vehicles
GET http://localhost:8080/api/vehicles?keyword=Toyota
GET http://localhost:8080/api/vehicles?hangXe=Hyundai&soCho=5&minPrice=500000&maxPrice=1500000
```

### Xem chi tiết xe

```http
GET http://localhost:8080/api/vehicles/1
```

### Đặt xe

```http
POST http://localhost:8080/api/rental-orders
Content-Type: application/json

{
  "customerId": 1,
  "vehicleId": 1,
  "thoiGianNhan": "2026-06-01T09:00:00",
  "thoiGianTra": "2026-06-03T18:00:00",
  "diaDiemNhan": "Sân bay Tân Sơn Nhất",
  "diaDiemTra": "Sân bay Tân Sơn Nhất"
}
```

### Thanh toán

Sau khi tạo đơn, lấy `id` đơn thuê trả về rồi thanh toán:

```http
POST http://localhost:8080/api/payments
Content-Type: application/json

{
  "rentalOrderId": 1,
  "phuongThuc": "MOMO",
  "maGiaoDich": "GD_DEMO_001"
}
```

### Theo dõi đơn thuê

```http
GET http://localhost:8080/api/rental-orders/1
GET http://localhost:8080/api/rental-orders/customer/1
```

### Xem thanh toán theo đơn

```http
GET http://localhost:8080/api/payments/order/1
```

## 7. API phụ: đăng ký đối tác

```http
POST http://localhost:8080/api/partner-registrations
Content-Type: application/json

{
  "userId": 1,
  "tenDoiTac": "Hộ kinh doanh xe tự lái Minh Anh",
  "loaiDoiTac": "HO_KINH_DOANH",
  "soDienThoai": "0909999888",
  "email": "minhanh@example.com",
  "diaChi": "TP.HCM",
  "maSoThue": "1234567890",
  "ghiChu": "Muốn đăng ký cho thuê 2 xe gia đình"
}
```

```http
GET http://localhost:8080/api/partner-registrations/1
```

## 8. Ghi chú demo

- Password trong bảng `nguoi_dung` đang lưu plain text để dễ demo. Làm thật phải mã hóa bằng BCrypt.
- Thanh toán là giả lập. Khi gọi `/api/payments`, hệ thống mặc định thanh toán thành công.
- Khi thanh toán thành công, đơn thuê chuyển sang `PAID`, xe chuyển sang `RENTED`.
- Demo chưa xử lý kiểm tra trùng lịch thuê xe theo từng ngày. Có thể bổ sung sau.
