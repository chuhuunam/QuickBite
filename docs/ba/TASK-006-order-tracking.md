# TASK-006: Theo dõi Đơn hàng

## 1. Tổng quan
Khách hàng theo dõi trạng thái đơn hàng từ lúc đặt đến khi nhận hàng.

## 2. User Stories

### US-006.1: Theo dõi đơn hàng realtime
> **As a** khách hàng đã đặt hàng
> **I want to** theo dõi trạng thái đơn hàng
> **So that** tôi biết khi nào nhận được đồ ăn

**Luồng trạng thái:**
```
Đặt hàng → Nhà hàng xác nhận → Đang chuẩn bị → Sẵn sàng giao
                                                    ↓
                              Đã giao ← Đang giao ← Tài xế nhận
```

### US-006.2: Xem vị trí tài xế
> **As a** khách hàng
> **I want to** xem vị trí tài xế trên bản đồ
> **So that** tôi biết tài xế đang ở đâu và còn bao lâu

**Luồng:**
1. Đơn hàng ở trạng thái "Đang giao"
2. Màn hình hiển thị bản đồ với vị trí tài xế
3. Vị trí cập nhật realtime (mỗi 30 giây)
4. Hiển thị thời gian dự kiến còn lại

### US-006.3: Liên hệ tài xế
> **As a** khách hàng
> **I want to** gọi điện cho tài xế
> **So that** tôi hướng dẫn địa chỉ hoặc hỏi thông tin

**Luồng:**
1. Nhấn nút "Gọi tài xế"
2. Mở ứng dụng gọi điện với số tài xế

## 3. Trạng thái Đơn hàng

| Trạng thái | Mô tả | Icon |
|------------|-------|------|
| Chờ xác nhận | Đơn mới, chờ nhà hàng | 🕐 |
| Đã xác nhận | Nhà hàng đã nhận đơn | ✅ |
| Đang chuẩn bị | Nhà hàng đang nấu | 👨‍🍳 |
| Sẵn sàng | Chờ tài xế đến lấy | 📦 |
| Đang giao | Tài xế đang di chuyển | 🛵 |
| Đã giao | Hoàn thành | ✅ |
| Đã hủy | Đơn bị hủy | ❌ |

## 4. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Hủy đơn | Chỉ hủy được khi chưa "Đang chuẩn bị" |
| BR-02 | Thời gian cập nhật | Vị trí tài xế cập nhật mỗi 30 giây |
| BR-03 | Thông báo | Push notification khi đổi trạng thái |

## 5. Wireframe

### Màn hình Theo dõi đơn hàng
```
┌─────────────────────────────┐
│ ←  Đơn hàng #12345          │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │                         │ │
│ │       [BẢN ĐỒ]          │ │
│ │    🛵 ←── 📍            │ │
│ │                         │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│  🛵 Đang giao hàng          │
│  Còn khoảng 10 phút         │
├─────────────────────────────┤
│ 👤 Nguyễn Văn A             │
│ 🛵 59A-12345                │
│ ⭐ 4.8                      │
│                             │
│  [📞 Gọi]    [💬 Nhắn tin]  │
├─────────────────────────────┤
│ ● Đã đặt hàng      12:00    │
│ ● Đã xác nhận      12:02    │
│ ● Đang chuẩn bị    12:05    │
│ ● Sẵn sàng giao    12:20    │
│ ● Đang giao        12:25    │
│ ○ Đã giao          --:--    │
└─────────────────────────────┘
```

## 6. Thông báo Push

| Sự kiện | Nội dung |
|---------|----------|
| Xác nhận | "Nhà hàng Phở 24 đã nhận đơn của bạn" |
| Đang chuẩn bị | "Đơn hàng của bạn đang được chuẩn bị" |
| Tài xế nhận | "Tài xế Nguyễn Văn A đang đến lấy đơn" |
| Đang giao | "Tài xế đang trên đường giao hàng" |
| Đã giao | "Đơn hàng đã được giao. Chúc ngon miệng!" |

## 7. Acceptance Criteria
- [ ] Hiển thị timeline trạng thái đơn hàng
- [ ] Bản đồ hiển thị vị trí tài xế (khi đang giao)
- [ ] Có thể gọi điện cho tài xế
- [ ] Push notification khi đổi trạng thái
- [ ] Hiển thị thời gian dự kiến
- [ ] Nút hủy đơn (nếu chưa chuẩn bị)

## 8. Liên kết
- **Trước:** TASK-005 Giỏ hàng & Đặt hàng
- **Related:** TASK-013 Luồng tài xế
