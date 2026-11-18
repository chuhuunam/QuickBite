# TASK-011: Luồng Chủ nhà hàng (Restaurant Owner)

## 1. Tổng quan
Chủ nhà hàng quản lý thông tin nhà hàng, menu và xử lý đơn hàng.

## 2. User Stories

### US-011.1: Đăng ký nhà hàng
> **As a** chủ nhà hàng
> **I want to** đăng ký nhà hàng lên QuickBite
> **So that** khách hàng có thể đặt món

**Luồng:**
1. Chủ nhà hàng đăng ký tài khoản (role: RESTAURANT_OWNER)
2. Vào mục "Đăng ký nhà hàng"
3. Nhập: Tên, Địa chỉ, Mô tả, Giờ hoạt động
4. Upload ảnh đại diện
5. Gửi đăng ký → Chờ duyệt
6. Duyệt xong → Có thể thêm menu

### US-011.2: Quản lý Menu
> **As a** chủ nhà hàng
> **I want to** quản lý menu: thêm/sửa/xóa món
> **So that** khách hàng xem được menu mới nhất

**Luồng thêm món:**
1. Vào Menu → Thêm danh mục (nếu chưa có)
2. Trong danh mục → Thêm món
3. Nhập: Tên, Mô tả, Giá, Ảnh
4. Lưu → Món hiển thị trên app khách

**Tắt/Bật món:**
1. Món hết nguyên liệu → Toggle "Hết hàng"
2. Món hiển thị xám trên app khách
3. Có hàng lại → Toggle bật

### US-011.3: Xử lý đơn hàng
> **As a** chủ nhà hàng
> **I want to** xem và xử lý đơn hàng mới
> **So that** tôi chuẩn bị món cho khách

**Luồng:**
1. Có đơn mới → Thông báo push + âm thanh
2. Xem chi tiết đơn: Các món, ghi chú, địa chỉ giao
3. Nhấn "Xác nhận" → Bắt đầu chuẩn bị
4. Chuẩn bị xong → Nhấn "Sẵn sàng giao"
5. Hệ thống tìm tài xế

**Hủy đơn:**
1. Nếu không thể thực hiện → Nhấn "Từ chối"
2. Chọn lý do: Hết món, Quá tải, Đóng cửa
3. Khách nhận thông báo và hoàn tiền (nếu đã thanh toán)

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Thời gian xác nhận | Phải xác nhận đơn trong 5 phút |
| BR-02 | Tự động hủy | Không xác nhận sau 5 phút → Tự động hủy |
| BR-03 | Giờ hoạt động | Chỉ nhận đơn trong giờ hoạt động |
| BR-04 | Một nhà hàng/tài khoản | Mỗi owner quản lý 1 nhà hàng |

## 4. Wireframe

### Dashboard Nhà hàng
```
┌─────────────────────────────┐
│ 🏪 Phở 24           ⚙️     │
├─────────────────────────────┤
│ Trạng thái: 🟢 Đang hoạt động│
├─────────────────────────────┤
│ Hôm nay                     │
│ ┌─────┐ ┌─────┐ ┌─────┐     │
│ │ 15  │ │ 12  │ │ 2.5M│     │
│ │Đơn  │ │Xong │ │ VNĐ │     │
│ └─────┘ └─────┘ └─────┘     │
├─────────────────────────────┤
│ 🔔 Đơn mới (3)              │
│ ┌─────────────────────────┐ │
│ │ #12347 • 2 món • 12:30  │ │
│ │ [Xem] [Xác nhận] [Hủy]  │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ #12346 • 3 món • 12:28  │ │
│ │ [Xem] [Xác nhận] [Hủy]  │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ 👨‍🍳 Đang chuẩn bị (2)        │
│ #12345 • 12:25 • ⏱️ 10 phút │
└─────────────────────────────┘
```

### Màn hình Chi tiết Đơn
```
┌─────────────────────────────┐
│ ←  Đơn #12347               │
├─────────────────────────────┤
│ 🕐 12:30 • COD              │
├─────────────────────────────┤
│ Món:                        │
│ • 2x Phở Bò Tái             │
│   "Không hành"              │
│ • 1x Nước mía               │
├─────────────────────────────┤
│ Tổng: 165,000đ              │
├─────────────────────────────┤
│ Giao đến:                   │
│ 📍 456 Nguyễn Huệ, Q1       │
│ 📞 0909876543               │
├─────────────────────────────┤
│                             │
│ [TỪ CHỐI]       [XÁC NHẬN]  │
└─────────────────────────────┘
```

### Quản lý Menu
```
┌─────────────────────────────┐
│ ←  Quản lý Menu             │
├─────────────────────────────┤
│ PHỞ                   [+ Món]│
│ ├─ Phở Bò Tái    55,000đ ✓ │
│ ├─ Phở Gà        50,000đ ✓ │
│ └─ Phở Đặc biệt  65,000đ ✗ │
├─────────────────────────────┤
│ NƯỚC UỐNG             [+ Món]│
│ ├─ Nước mía      15,000đ ✓ │
│ └─ Trà đá        10,000đ ✓ │
├─────────────────────────────┤
│ [+ Thêm danh mục]           │
└─────────────────────────────┘
```

## 5. Trạng thái Đơn hàng (Góc nhà hàng)

| Trạng thái | Mô tả | Action |
|------------|-------|--------|
| Chờ xác nhận | Đơn mới | Xác nhận / Từ chối |
| Đang chuẩn bị | Đã xác nhận | Sẵn sàng giao |
| Chờ tài xế | Đã xong, chờ lấy | - |
| Đã lấy | Tài xế đã lấy | - |

## 6. Acceptance Criteria
- [ ] Đăng ký nhà hàng với thông tin cơ bản
- [ ] Dashboard hiển thị đơn mới và thống kê
- [ ] Thêm/sửa/xóa danh mục và món
- [ ] Toggle hết hàng/còn hàng
- [ ] Nhận notification đơn mới
- [ ] Xác nhận/Từ chối đơn
- [ ] Đánh dấu "Sẵn sàng giao"

## 7. Liên kết
- **Related:** TASK-006 Theo dõi đơn hàng (trạng thái sync)
- **Related:** TASK-010 Luồng tài xế
