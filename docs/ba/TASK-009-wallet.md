# TASK-009: Ví điện tử QuickBite

## 1. Tổng quan
Khách hàng sử dụng ví điện tử để thanh toán nhanh, tích điểm và nhận khuyến mãi.

## 2. User Stories

### US-009.1: Xem số dư ví
> **As a** khách hàng
> **I want to** xem số dư ví của tôi
> **So that** tôi biết còn bao nhiêu tiền để thanh toán

### US-009.2: Nạp tiền vào ví
> **As a** khách hàng
> **I want to** nạp tiền vào ví QuickBite
> **So that** tôi thanh toán nhanh không cần tiền mặt

**Luồng:**
1. Khách vào màn hình Ví
2. Nhấn "Nạp tiền"
3. Chọn số tiền nạp (hoặc nhập tùy chỉnh)
4. Chọn phương thức: Thẻ ngân hàng, Chuyển khoản
5. Xác nhận thanh toán
6. Tiền được cộng vào ví

### US-009.3: Xem lịch sử giao dịch
> **As a** khách hàng
> **I want to** xem lịch sử giao dịch ví
> **So that** tôi theo dõi tiền vào/ra

**Loại giao dịch:**
- Nạp tiền (+)
- Thanh toán đơn hàng (-)
- Hoàn tiền (+)
- Khuyến mãi (+)

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Nạp tối thiểu | Nạp tối thiểu 10,000đ |
| BR-02 | Nạp tối đa | Nạp tối đa 10,000,000đ/lần |
| BR-03 | Số dư tối đa | Số dư ví tối đa 50,000,000đ |
| BR-04 | Không rút tiền | Tiền trong ví không rút được (chỉ dùng thanh toán) |

## 4. Wireframe

### Màn hình Ví
```
┌─────────────────────────────┐
│ ←  Ví QuickBite             │
├─────────────────────────────┤
│                             │
│      Số dư khả dụng         │
│                             │
│      500,000 đ              │
│                             │
│  [   NẠP TIỀN   ]           │
│                             │
├─────────────────────────────┤
│ Lịch sử giao dịch           │
├─────────────────────────────┤
│ 15/01 • Thanh toán #12345   │
│ Phở 24              -175,000│
├─────────────────────────────┤
│ 14/01 • Nạp tiền            │
│ Thẻ ****1234        +200,000│
├─────────────────────────────┤
│ 13/01 • Hoàn tiền #12300    │
│ Đơn hủy             +120,000│
├─────────────────────────────┤
│ 12/01 • Khuyến mãi          │
│ Mã WELCOME50         +50,000│
└─────────────────────────────┘
```

### Màn hình Nạp tiền
```
┌─────────────────────────────┐
│ ←  Nạp tiền                 │
├─────────────────────────────┤
│ Chọn số tiền                │
│                             │
│ [100K] [200K] [500K] [1M]   │
│                             │
│ Hoặc nhập số tiền           │
│ ┌─────────────────────────┐ │
│ │ 300,000              đ  │ │
│ └─────────────────────────┘ │
│                             │
│ Phương thức thanh toán      │
│ ┌─────────────────────────┐ │
│ │ 💳 Thẻ Visa ****1234  > │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🏦 Chuyển khoản       > │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │  NẠP 300,000 Đ          │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 5. Loại Giao dịch

| Loại | Icon | Mô tả |
|------|------|-------|
| TOPUP | + (xanh) | Nạp tiền vào ví |
| PAYMENT | - (đỏ) | Thanh toán đơn hàng |
| REFUND | + (xanh) | Hoàn tiền đơn hủy |
| BONUS | + (vàng) | Khuyến mãi/thưởng |

## 6. Acceptance Criteria
- [ ] Hiển thị số dư ví hiện tại
- [ ] Nạp tiền với các mệnh giá gợi ý
- [ ] Hỗ trợ thẻ ngân hàng và chuyển khoản
- [ ] Hiển thị lịch sử giao dịch với bộ lọc
- [ ] Thông báo khi nạp thành công
- [ ] Validation số tiền nạp

## 7. Liên kết
- **Related:** TASK-012 Thanh toán (sử dụng ví)
