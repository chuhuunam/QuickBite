# TASK-010: Luồng Tài xế (Driver App)

## 1. Tổng quan
Tài xế nhận và giao đơn hàng, cập nhật trạng thái và nhận thanh toán.

## 2. User Stories

### US-010.1: Đăng ký làm tài xế
> **As a** người muốn làm tài xế
> **I want to** đăng ký tài khoản tài xế
> **So that** tôi có thể nhận đơn giao hàng

**Luồng:**
1. Người dùng vào mục "Đăng ký tài xế"
2. Nhập thông tin: Loại xe, Biển số
3. Upload ảnh CCCD, Bằng lái (Phase sau)
4. Gửi đăng ký
5. Chờ duyệt (tự động duyệt MVP)
6. Được phép bật chế độ Online

### US-010.2: Bật/Tắt chế độ nhận đơn
> **As a** tài xế
> **I want to** bật/tắt chế độ online
> **So that** tôi kiểm soát khi nào nhận đơn

**Luồng:**
1. Tài xế mở app
2. Toggle "Online/Offline"
3. Online → Bắt đầu nhận thông báo đơn mới
4. Offline → Không nhận đơn mới

### US-010.3: Nhận đơn giao hàng
> **As a** tài xế online
> **I want to** nhận thông báo có đơn mới
> **So that** tôi chọn đơn phù hợp

**Luồng:**
1. Có đơn mới trong khu vực
2. Thông báo push: "Đơn mới - Phở 24 → Q1, 25,000đ"
3. Tài xế xem chi tiết: Điểm lấy, Điểm giao, Phí ship
4. Nhấn "Nhận đơn" hoặc bỏ qua
5. Nếu nhận → Chuyển sang màn hình chi tiết đơn

### US-010.4: Thực hiện giao hàng
> **As a** tài xế đã nhận đơn
> **I want to** cập nhật trạng thái giao hàng
> **So that** khách hàng và hệ thống biết tiến độ

**Luồng:**
1. Tài xế đến nhà hàng → Nhấn "Đã đến lấy hàng"
2. Lấy hàng xong → Nhấn "Đã lấy hàng"
3. Đến địa chỉ khách → Nhấn "Đã đến nơi"
4. Giao hàng xong → Nhấn "Hoàn thành"
5. COD: Xác nhận đã nhận tiền
6. Hiển thị thu nhập chuyến đi

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Một đơn/lần | Tài xế chỉ nhận 1 đơn tại một thời điểm |
| BR-02 | Timeout nhận đơn | Đơn tự hủy sau 60 giây không ai nhận |
| BR-03 | Cập nhật vị trí | GPS cập nhật mỗi 30 giây khi online |
| BR-04 | Hủy đơn | Tài xế hủy > 3 đơn/ngày → Cảnh báo |

## 4. Wireframe

### Màn hình Chính Tài xế
```
┌─────────────────────────────┐
│ 👤 Nguyễn Văn A      ⚙️     │
├─────────────────────────────┤
│                             │
│      Hôm nay: 350,000đ      │
│      5 chuyến               │
│                             │
│      ┌─────────────┐        │
│      │   ONLINE    │        │
│      └─────────────┘        │
│                             │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │                         │ │
│ │       [BẢN ĐỒ]          │ │
│ │                         │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ Đang chờ đơn hàng...        │
└─────────────────────────────┘
```

### Popup Đơn mới
```
┌─────────────────────────────┐
│      ĐƠN HÀNG MỚI!          │
│         ⏱️ 45s              │
├─────────────────────────────┤
│ 🏪 Phở 24                   │
│ 📍 123 Lê Lợi, Q1           │
│           ↓ 2.5km           │
│ 🏠 456 Nguyễn Huệ, Q1       │
├─────────────────────────────┤
│ 💰 Thu nhập: 25,000đ        │
│ 💳 COD: 175,000đ            │
├─────────────────────────────┤
│                             │
│  [Bỏ qua]      [NHẬN ĐƠN]   │
└─────────────────────────────┘
```

### Màn hình Thực hiện đơn
```
┌─────────────────────────────┐
│ ←  Đơn #12345               │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │       [BẢN ĐỒ]          │ │
│ │   🛵 ───── 🏪           │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ 📍 Điểm lấy hàng            │
│ Phở 24                      │
│ 123 Lê Lợi, Q1              │
│ [📞 Gọi] [🗺️ Chỉ đường]     │
├─────────────────────────────┤
│ 📦 Đơn hàng                 │
│ 2x Phở Bò, 1x Nước mía      │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │  ĐÃ ĐẾN NHẬN HÀNG       │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 5. Trạng thái Tài xế

| Trạng thái | Mô tả |
|------------|-------|
| OFFLINE | Không nhận đơn |
| ONLINE | Đang chờ đơn |
| BUSY | Đang thực hiện đơn |

## 6. Acceptance Criteria
- [ ] Tài xế đăng ký với loại xe, biển số
- [ ] Toggle Online/Offline
- [ ] Nhận notification đơn mới với countdown
- [ ] Xem chi tiết đơn trước khi nhận
- [ ] Cập nhật trạng thái: Đến lấy → Lấy xong → Đến nơi → Hoàn thành
- [ ] Xử lý COD: Xác nhận đã nhận tiền
- [ ] Hiển thị thu nhập

## 7. Liên kết
- **Related:** TASK-006 Theo dõi đơn hàng (phía khách)
- **Related:** TASK-012 Thanh toán COD
