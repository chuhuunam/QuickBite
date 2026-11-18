# TASK-001: Đăng ký Tài khoản Khách hàng

## 1. Tổng quan
Người dùng mới cần đăng ký tài khoản để có thể đặt đồ ăn, lưu địa chỉ giao hàng và theo dõi đơn hàng.

## 2. User Stories

### US-001.1: Đăng ký bằng Email
> **As a** người dùng mới
> **I want to** đăng ký tài khoản bằng email
> **So that** tôi có thể đặt đồ ăn và theo dõi đơn hàng

**Luồng chính:**
1. Người dùng mở app/web và nhấn "Đăng ký"
2. Hệ thống hiển thị form đăng ký
3. Người dùng nhập: Email, Mật khẩu, Họ tên
4. Nhấn "Đăng ký"
5. Hệ thống kiểm tra email chưa tồn tại
6. Hệ thống tạo tài khoản và tự động đăng nhập
7. Hiển thị màn hình chính với thông báo "Chào mừng [Tên]!"

**Luồng thay thế:**
- 5a. Email đã tồn tại → Hiển thị "Email này đã được sử dụng"
- 4a. Mật khẩu yếu → Hiển thị "Mật khẩu phải có ít nhất 8 ký tự"

### US-001.2: Xác thực Email (Optional - Phase sau)
> **As a** người dùng mới đăng ký
> **I want to** xác thực email của tôi
> **So that** tài khoản được bảo mật hơn

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Email unique | Mỗi email chỉ được đăng ký một lần |
| BR-02 | Mật khẩu tối thiểu | Mật khẩu ít nhất 8 ký tự |
| BR-03 | Email lowercase | Email được chuyển thành chữ thường trước khi lưu |
| BR-04 | Role mặc định | Người dùng mới có role CUSTOMER |

## 4. Wireframe/Mockup

### Màn hình Đăng ký
```
┌─────────────────────────────┐
│         QuickBite           │
│                             │
│  ┌───────────────────────┐  │
│  │ Email                 │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │ Mật khẩu          👁  │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │ Họ và tên             │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │      ĐĂNG KÝ          │  │
│  └───────────────────────┘  │
│                             │
│  Đã có tài khoản? Đăng nhập │
└─────────────────────────────┘
```

## 5. Validation Rules

| Field | Rule |
|-------|------|
| Email | Bắt buộc, format email hợp lệ |
| Mật khẩu | Bắt buộc, tối thiểu 8 ký tự |
| Họ tên | Không bắt buộc |

## 6. Acceptance Criteria
- [ ] Form đăng ký hiển thị đầy đủ 3 trường
- [ ] Validate email format trước khi submit
- [ ] Thông báo lỗi rõ ràng khi email đã tồn tại
- [ ] Tự động đăng nhập sau khi đăng ký thành công
- [ ] Chuyển đến màn hình chính sau đăng ký

## 7. Liên kết
- **Tiếp theo:** TASK-006 Đăng nhập
- **Related:** TASK-007 Quản lý hồ sơ
