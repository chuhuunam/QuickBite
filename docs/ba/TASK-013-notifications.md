# TASK-013: Hệ thống Thông báo

## 1. Tổng quan
Hệ thống gửi thông báo đến người dùng (khách, tài xế, nhà hàng) về trạng thái đơn hàng và các sự kiện quan trọng.

## 2. User Stories

### US-013.1: Nhận thông báo đơn hàng
> **As a** khách hàng
> **I want to** nhận thông báo về đơn hàng của tôi
> **So that** tôi biết tiến độ giao hàng

### US-013.2: Quản lý cài đặt thông báo
> **As a** người dùng
> **I want to** bật/tắt các loại thông báo
> **So that** tôi chỉ nhận thông báo quan trọng

## 3. Các loại Thông báo

### Khách hàng

| Sự kiện | Nội dung | Kênh |
|---------|----------|------|
| Đơn được xác nhận | "Nhà hàng {name} đã xác nhận đơn hàng của bạn" | Push |
| Đang chuẩn bị | "Đơn hàng của bạn đang được chuẩn bị" | Push |
| Tài xế nhận đơn | "Tài xế {name} đang đến lấy đơn hàng" | Push |
| Đang giao | "Đơn hàng đang trên đường giao đến bạn" | Push |
| Đã giao | "Đơn hàng đã được giao. Chúc ngon miệng!" | Push |
| Đơn bị hủy | "Đơn hàng đã bị hủy. Lý do: {reason}" | Push |
| Hoàn tiền | "Bạn đã được hoàn {amount} vào ví" | Push |

### Tài xế

| Sự kiện | Nội dung | Kênh |
|---------|----------|------|
| Đơn mới | "Đơn mới! {restaurant} → {distance}km, {earnings}đ" | Push + Sound |
| Nhắc nhở | "Bạn có đơn đang chờ xử lý" | Push |

### Nhà hàng

| Sự kiện | Nội dung | Kênh |
|---------|----------|------|
| Đơn mới | "Đơn mới #{id} - {itemCount} món" | Push + Sound |
| Nhắc xác nhận | "Đơn #{id} chưa được xác nhận (còn 2 phút)" | Push |
| Tài xế đến | "Tài xế {name} đang đến lấy đơn #{id}" | Push |

## 4. Cài đặt Thông báo

| Loại | Mặc định | Có thể tắt |
|------|----------|------------|
| Trạng thái đơn hàng | Bật | Không |
| Khuyến mãi | Bật | Có |
| Tin tức | Tắt | Có |
| Nhắc nhở đánh giá | Bật | Có |

## 5. Wireframe

### Màn hình Cài đặt Thông báo
```
┌─────────────────────────────┐
│ ←  Thông báo                │
├─────────────────────────────┤
│ Đơn hàng                    │
│ ├─ Trạng thái đơn      [✓]  │
│ └─ Nhắc đánh giá       [✓]  │
├─────────────────────────────┤
│ Khuyến mãi                  │
│ ├─ Mã giảm giá         [✓]  │
│ └─ Ưu đãi nhà hàng     [ ]  │
├─────────────────────────────┤
│ Khác                        │
│ ├─ Tin tức             [ ]  │
│ └─ Cập nhật ứng dụng   [✓]  │
└─────────────────────────────┘
```

### Màn hình Danh sách Thông báo
```
┌─────────────────────────────┐
│ ←  Thông báo                │
├─────────────────────────────┤
│ Hôm nay                     │
│ ┌─────────────────────────┐ │
│ │ 🛵 Đơn hàng đã giao     │ │
│ │ Chúc ngon miệng!        │ │
│ │ 12:45                   │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 👨‍🍳 Đang chuẩn bị        │ │
│ │ Phở 24 đang nấu món     │ │
│ │ 12:30                   │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ Hôm qua                     │
│ ┌─────────────────────────┐ │
│ │ 🎉 Mã giảm 20%          │ │
│ │ Dùng mã SAVE20 hôm nay  │ │
│ │ 10:00                   │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 6. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Không tắt critical | Thông báo đơn hàng không thể tắt |
| BR-02 | Quiet hours | Không gửi promo 22:00 - 08:00 |
| BR-03 | Lưu 30 ngày | Thông báo lưu trong 30 ngày |

## 7. Kênh Gửi

| Kênh | Mô tả | Sử dụng |
|------|-------|---------|
| Push | Firebase Cloud Messaging | Chính |
| In-app | Hiển thị trong app | Luôn luôn |
| Email | Gửi email | Order confirmation, Marketing |
| SMS | Tin nhắn SMS | OTP, Critical only |

## 8. Acceptance Criteria
- [ ] Push notification cho các sự kiện đơn hàng
- [ ] Danh sách thông báo trong app
- [ ] Cài đặt bật/tắt từng loại thông báo
- [ ] Badge số thông báo chưa đọc
- [ ] Nhấn thông báo → Mở màn hình liên quan

## 9. Liên kết
- **Related:** Tất cả task liên quan đến đơn hàng
