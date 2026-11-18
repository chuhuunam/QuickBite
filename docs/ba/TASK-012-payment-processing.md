# TASK-012: Xử lý Thanh toán

## 1. Tổng quan
Khách hàng cần thanh toán đơn hàng sau khi đặt món. Hệ thống hỗ trợ nhiều phương thức thanh toán khác nhau.

## 2. User Stories

### US-012.1: Thanh toán bằng Ví điện tử
> **As a** khách hàng đã nạp tiền vào ví
> **I want to** thanh toán đơn hàng bằng số dư ví
> **So that** tôi không cần nhập thông tin thanh toán mỗi lần

**Luồng chính:**
1. Khách chọn món và vào giỏ hàng
2. Khách nhấn "Đặt hàng"
3. Hệ thống hiển thị màn hình thanh toán với tổng tiền
4. Khách chọn "Ví QuickBite" làm phương thức thanh toán
5. Hệ thống kiểm tra số dư ví
6. Nếu đủ tiền → Trừ tiền ví → Tạo đơn hàng → Hiển thị "Đặt hàng thành công"
7. Thông báo cho nhà hàng có đơn mới

**Luồng thay thế:**
- 5a. Nếu không đủ tiền → Hiển thị "Số dư không đủ, vui lòng nạp thêm hoặc chọn phương thức khác"

### US-012.2: Thanh toán khi nhận hàng (COD)
> **As a** khách hàng chưa muốn thanh toán online
> **I want to** chọn thanh toán khi nhận hàng
> **So that** tôi trả tiền mặt cho tài xế

**Luồng chính:**
1. Khách chọn "Thanh toán khi nhận hàng"
2. Hệ thống tạo đơn hàng với trạng thái "Chờ thanh toán"
3. Thông báo cho nhà hàng
4. Khi tài xế giao hàng xong, tài xế xác nhận đã nhận tiền
5. Đơn hàng chuyển sang "Đã thanh toán"

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Số dư ví | Chỉ cho phép thanh toán ví nếu số dư >= tổng đơn hàng |
| BR-02 | Một thanh toán/đơn | Mỗi đơn hàng chỉ có một thanh toán |
| BR-03 | Không hoàn tiền tự động | Hoàn tiền do admin xử lý thủ công |
| BR-04 | COD chỉ cho đơn nhỏ | COD chỉ áp dụng với đơn <= 2,000,000 VND |

## 4. Trạng thái Thanh toán

```
┌──────────┐    Ví đủ tiền    ┌───────────┐
│  Chờ TT  │ ───────────────► │ Thành công│
└──────────┘                  └───────────┘
     │
     │ Ví không đủ
     ▼
┌──────────┐
│ Thất bại │
└──────────┘
```

**COD:**
```
┌──────────┐   Tài xế xác nhận   ┌───────────┐
│  Chờ TT  │ ──────────────────► │ Thành công│
└──────────┘                     └───────────┘
```

## 5. Màn hình liên quan

### Màn hình Thanh toán
- Hiển thị tổng tiền đơn hàng
- Danh sách phương thức thanh toán (Ví, COD)
- Số dư ví hiện tại
- Nút "Xác nhận thanh toán"

### Thông báo kết quả
- Thanh toán thành công: Hiển thị mã đơn hàng, thời gian dự kiến
- Thanh toán thất bại: Hiển thị lý do, nút thử lại

## 6. Acceptance Criteria
- [ ] Khách có thể thanh toán bằng ví nếu đủ số dư
- [ ] Khách có thể chọn COD cho đơn <= 2 triệu
- [ ] Nhà hàng nhận thông báo khi có đơn mới thanh toán xong
- [ ] Lịch sử thanh toán hiển thị trong chi tiết đơn hàng

## 7. Câu hỏi mở
- [ ] Có hỗ trợ thẻ ngân hàng/VNPay không? → Phase sau
- [ ] Chính sách hoàn tiền như thế nào? → Cần BA riêng
