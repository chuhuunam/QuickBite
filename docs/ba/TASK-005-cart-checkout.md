# TASK-005: Giỏ hàng & Đặt hàng

## 1. Tổng quan
Khách hàng xem giỏ hàng, chỉnh sửa số lượng và tiến hành đặt hàng.

## 2. User Stories

### US-005.1: Xem giỏ hàng
> **As a** khách hàng
> **I want to** xem các món đã thêm vào giỏ
> **So that** tôi kiểm tra trước khi đặt

**Thông tin hiển thị:**
- Tên nhà hàng
- Danh sách món: Tên, số lượng, giá, ghi chú
- Tạm tính
- Phí giao hàng
- Tổng cộng

### US-005.2: Chỉnh sửa giỏ hàng
> **As a** khách hàng
> **I want to** thay đổi số lượng hoặc xóa món
> **So that** tôi điều chỉnh đơn hàng theo ý muốn

**Luồng:**
1. Trong giỏ hàng, nhấn +/- để thay đổi số lượng
2. Nếu số lượng = 0 → Xóa món khỏi giỏ
3. Vuốt trái để xóa nhanh
4. Nút "Xóa tất cả" để làm trống giỏ

### US-005.3: Đặt hàng
> **As a** khách hàng
> **I want to** đặt hàng từ giỏ hàng
> **So that** nhà hàng chuẩn bị và giao cho tôi

**Luồng chính:**
1. Khách nhấn "Đặt hàng"
2. Hệ thống hiển thị màn hình xác nhận:
   - Địa chỉ giao hàng (có thể thay đổi)
   - Phương thức thanh toán
   - Chi tiết đơn hàng
   - Tổng tiền
3. Khách xác nhận địa chỉ
4. Khách chọn phương thức thanh toán
5. Nhấn "Xác nhận đặt hàng"
6. Thanh toán (nếu ví) → Xem TASK-012
7. Hiển thị "Đặt hàng thành công"
8. Chuyển đến màn hình theo dõi đơn hàng

**Luồng thay thế:**
- 1a. Chưa đăng nhập → Yêu cầu đăng nhập trước
- 2a. Chưa có địa chỉ → Yêu cầu thêm địa chỉ

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Đơn tối thiểu | Giá trị đơn tối thiểu 30,000đ |
| BR-02 | Phí giao hàng | Cố định 15,000đ (phase đầu) |
| BR-03 | Giỏ hết hạn | Giỏ hàng guest hết hạn sau 7 ngày |
| BR-04 | Giá real-time | Giá món có thể thay đổi, cập nhật khi checkout |

## 4. Wireframe

### Màn hình Giỏ hàng
```
┌─────────────────────────────┐
│ ←  Giỏ hàng                 │
├─────────────────────────────┤
│ 🏪 Phở 24                   │
├─────────────────────────────┤
│ Phở Bò Tái          55,000đ │
│ Không hành                  │
│ [-]  2  [+]        110,000đ │
├─────────────────────────────┤
│ Phở Gà              50,000đ │
│ [-]  1  [+]         50,000đ │
├─────────────────────────────┤
│                             │
│ Tạm tính            160,000đ│
│ Phí giao hàng        15,000đ│
│ ─────────────────────────── │
│ Tổng cộng           175,000đ│
│                             │
│ ┌─────────────────────────┐ │
│ │  ĐẶT HÀNG - 175,000đ    │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

### Màn hình Xác nhận đặt hàng
```
┌─────────────────────────────┐
│ ←  Xác nhận đơn hàng        │
├─────────────────────────────┤
│ 📍 Giao đến                 │
│ ┌─────────────────────────┐ │
│ │ 🏠 Nhà                   │ │
│ │ 123 Nguyễn Huệ, Q1    > │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ 💳 Thanh toán               │
│ ┌─────────────────────────┐ │
│ │ 👛 Ví QuickBite       > │ │
│ │ Số dư: 500,000đ         │ │
│ └─────────────────────────┘ │
├─────────────────────────────┤
│ 📝 Chi tiết đơn hàng        │
│ 2x Phở Bò Tái      110,000đ │
│ 1x Phở Gà           50,000đ │
│ Phí giao hàng       15,000đ │
│ ─────────────────────────── │
│ Tổng cộng          175,000đ │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │  XÁC NHẬN ĐẶT HÀNG      │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 5. Acceptance Criteria
- [ ] Hiển thị danh sách món trong giỏ với giá và số lượng
- [ ] Có thể +/- số lượng, xóa món
- [ ] Hiển thị tạm tính, phí giao, tổng cộng
- [ ] Chọn/thêm địa chỉ giao hàng
- [ ] Chọn phương thức thanh toán
- [ ] Thông báo nếu đơn < 30,000đ
- [ ] Chuyển đến theo dõi đơn sau khi đặt thành công

## 6. Liên kết
- **Trước:** TASK-004 Chi tiết nhà hàng
- **Sau:** TASK-006 Theo dõi đơn hàng
- **Related:** TASK-012 Thanh toán
