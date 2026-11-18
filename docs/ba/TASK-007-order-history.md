# TASK-007: Lịch sử Đơn hàng

## 1. Tổng quan
Khách hàng xem lại các đơn hàng đã đặt, có thể đặt lại hoặc đánh giá.

## 2. User Stories

### US-007.1: Xem lịch sử đơn hàng
> **As a** khách hàng
> **I want to** xem danh sách các đơn đã đặt
> **So that** tôi theo dõi chi tiêu và tìm đơn cũ

**Thông tin hiển thị:**
- Tên nhà hàng
- Ngày đặt
- Tổng tiền
- Trạng thái (Hoàn thành, Đã hủy)
- Số lượng món

### US-007.2: Đặt lại đơn cũ
> **As a** khách hàng
> **I want to** đặt lại đơn hàng cũ
> **So that** tôi nhanh chóng đặt lại món yêu thích

**Luồng:**
1. Khách xem chi tiết đơn cũ
2. Nhấn "Đặt lại"
3. Các món được thêm vào giỏ (nếu còn available)
4. Món không còn → Thông báo và bỏ qua
5. Chuyển đến giỏ hàng

### US-007.3: Đánh giá đơn hàng
> **As a** khách hàng
> **I want to** đánh giá sau khi nhận hàng
> **So that** tôi chia sẻ trải nghiệm với người khác

**Luồng:**
1. Đơn hàng hoàn thành
2. Hiển thị popup đánh giá (hoặc vào từ lịch sử)
3. Khách chọn số sao (1-5)
4. Khách viết nhận xét (optional)
5. Gửi đánh giá

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Thời hạn đánh giá | Chỉ đánh giá trong 7 ngày sau khi nhận |
| BR-02 | Một lần đánh giá | Mỗi đơn chỉ đánh giá 1 lần |
| BR-03 | Đặt lại | Giá có thể khác với đơn cũ |

## 4. Wireframe

### Màn hình Lịch sử Đơn hàng
```
┌─────────────────────────────┐
│ ←  Đơn hàng của tôi         │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ 🏪 Phở 24               │ │
│ │ 15/01/2024 • 2 món      │ │
│ │ 175,000đ    ✅ Hoàn thành│ │
│ │ [Đặt lại]  [Đánh giá]   │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🏪 Pizza Hut            │ │
│ │ 14/01/2024 • 1 món      │ │
│ │ 150,000đ    ❌ Đã hủy   │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🏪 Cơm Tấm Sài Gòn      │ │
│ │ 13/01/2024 • 3 món      │ │
│ │ 120,000đ    ✅ Hoàn thành│ │
│ │ ⭐⭐⭐⭐⭐ Đã đánh giá    │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

### Popup Đánh giá
```
┌─────────────────────────────┐
│      Đánh giá đơn hàng      │
│                             │
│      ⭐⭐⭐⭐☆              │
│        4/5 sao              │
│                             │
│ ┌─────────────────────────┐ │
│ │ Đồ ăn ngon, giao nhanh  │ │
│ │                         │ │
│ └─────────────────────────┘ │
│                             │
│  [Hủy]        [Gửi đánh giá]│
└─────────────────────────────┘
```

## 5. Acceptance Criteria
- [ ] Hiển thị danh sách đơn hàng đã đặt
- [ ] Phân loại: Hoàn thành, Đã hủy
- [ ] Nhấn vào đơn → Xem chi tiết
- [ ] Nút "Đặt lại" thêm món vào giỏ
- [ ] Popup đánh giá với sao và nhận xét
- [ ] Hiển thị đánh giá đã gửi

## 6. Liên kết
- **Related:** TASK-006 Theo dõi đơn hàng
- **Related:** TASK-004 Chi tiết nhà hàng (xem đánh giá)
