# TASK-002: Đăng nhập

## 1. Tổng quan
Khách hàng đã có tài khoản cần đăng nhập để truy cập các tính năng cá nhân: lịch sử đơn hàng, địa chỉ đã lưu, ví điện tử.

## 2. User Stories

### US-002.1: Đăng nhập bằng Email
> **As a** khách hàng đã có tài khoản
> **I want to** đăng nhập bằng email và mật khẩu
> **So that** tôi truy cập được tài khoản của mình

**Luồng chính:**
1. Khách nhấn "Đăng nhập" từ màn hình chào
2. Hệ thống hiển thị form đăng nhập
3. Khách nhập Email và Mật khẩu
4. Nhấn "Đăng nhập"
5. Hệ thống xác thực thông tin
6. Đăng nhập thành công → Chuyển đến màn hình chính
7. Hiển thị "Xin chào, [Tên]!"

**Luồng thay thế:**
- 5a. Sai email/mật khẩu → Hiển thị "Email hoặc mật khẩu không đúng"
- 5b. Tài khoản bị khóa → Hiển thị "Tài khoản đã bị khóa, vui lòng liên hệ hỗ trợ"

### US-002.2: Nhớ đăng nhập
> **As a** khách hàng
> **I want to** ứng dụng nhớ đăng nhập của tôi
> **So that** tôi không cần đăng nhập lại mỗi lần mở app

**Luồng:**
1. Khách đăng nhập thành công
2. Phiên đăng nhập được lưu (7 ngày)
3. Lần sau mở app → Tự động vào màn hình chính
4. Sau 7 ngày → Yêu cầu đăng nhập lại

### US-002.3: Đăng xuất
> **As a** khách hàng đã đăng nhập
> **I want to** đăng xuất khỏi tài khoản
> **So that** người khác không thể sử dụng tài khoản của tôi

**Luồng:**
1. Khách vào Cài đặt → Đăng xuất
2. Hệ thống xác nhận "Bạn có chắc muốn đăng xuất?"
3. Khách xác nhận
4. Chuyển về màn hình đăng nhập

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Phiên đăng nhập | Hết hạn sau 7 ngày không hoạt động |
| BR-02 | Giới hạn sai mật khẩu | Khóa tạm 15 phút sau 5 lần sai liên tiếp |
| BR-03 | Đăng nhập đồng thời | Cho phép đăng nhập trên nhiều thiết bị |

## 4. Wireframe

### Màn hình Đăng nhập
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
│  │      ĐĂNG NHẬP        │  │
│  └───────────────────────┘  │
│                             │
│      Quên mật khẩu?         │
│                             │
│  Chưa có tài khoản? Đăng ký │
└─────────────────────────────┘
```

## 5. Acceptance Criteria
- [ ] Form đăng nhập hiển thị Email và Mật khẩu
- [ ] Có nút ẩn/hiện mật khẩu
- [ ] Thông báo lỗi không tiết lộ thông tin (không nói rõ email hay mật khẩu sai)
- [ ] Giữ phiên đăng nhập trong 7 ngày
- [ ] Có link đến Đăng ký và Quên mật khẩu

## 6. Liên kết
- **Trước:** TASK-001 Đăng ký
- **Sau:** TASK-003 Quên mật khẩu (Phase sau)
