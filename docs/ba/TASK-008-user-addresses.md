# TASK-008: Quản lý Địa chỉ Giao hàng

## 1. Tổng quan
Khách hàng quản lý danh sách địa chỉ giao hàng để đặt hàng nhanh hơn.

## 2. User Stories

### US-008.1: Xem danh sách địa chỉ
> **As a** khách hàng
> **I want to** xem các địa chỉ đã lưu
> **So that** tôi chọn nhanh khi đặt hàng

### US-008.2: Thêm địa chỉ mới
> **As a** khách hàng
> **I want to** thêm địa chỉ giao hàng mới
> **So that** tôi có nhiều lựa chọn khi đặt

**Luồng:**
1. Khách nhấn "Thêm địa chỉ"
2. Hệ thống hiển thị form:
   - Nhãn (Nhà, Công ty, Khác)
   - Địa chỉ chi tiết
   - Ghi chú cho tài xế (optional)
3. Có thể chọn trên bản đồ
4. Nhấn "Lưu"
5. Địa chỉ được thêm vào danh sách

### US-008.3: Đặt địa chỉ mặc định
> **As a** khách hàng
> **I want to** đặt một địa chỉ làm mặc định
> **So that** không cần chọn mỗi lần đặt

**Luồng:**
1. Khách chọn địa chỉ
2. Nhấn "Đặt làm mặc định"
3. Địa chỉ cũ bỏ mặc định, địa chỉ mới thành mặc định

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Tối đa 5 địa chỉ | Mỗi khách tối đa 5 địa chỉ |
| BR-02 | Một mặc định | Chỉ có 1 địa chỉ mặc định |
| BR-03 | Địa chỉ đầu tiên | Địa chỉ đầu tiên tự động thành mặc định |
| BR-04 | Không xóa mặc định | Phải đổi mặc định trước khi xóa |

## 4. Wireframe

### Màn hình Danh sách Địa chỉ
```
┌─────────────────────────────┐
│ ←  Địa chỉ của tôi          │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ 🏠 Nhà            ⭐     │ │
│ │ 123 Nguyễn Huệ, Q1      │ │
│ │ Ghi chú: Gọi khi đến    │ │
│ │                   ✏️ 🗑️ │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🏢 Công ty               │ │
│ │ 456 Lê Lợi, Q1          │ │
│ │ Tầng 5, phòng 501       │ │
│ │                   ✏️ 🗑️ │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │  + Thêm địa chỉ mới     │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

### Form Thêm Địa chỉ
```
┌─────────────────────────────┐
│ ←  Thêm địa chỉ             │
├─────────────────────────────┤
│ Loại địa chỉ                │
│ [🏠 Nhà] [🏢 Cty] [📍 Khác] │
│                             │
│ Địa chỉ *                   │
│ ┌─────────────────────────┐ │
│ │ 123 Nguyễn Huệ, Quận 1  │ │
│ └─────────────────────────┘ │
│                             │
│ Ghi chú cho tài xế          │
│ ┌─────────────────────────┐ │
│ │ Gọi điện khi đến        │ │
│ └─────────────────────────┘ │
│                             │
│ ☐ Đặt làm địa chỉ mặc định  │
│                             │
│ ┌─────────────────────────┐ │
│ │        LƯU ĐỊA CHỈ      │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 5. Acceptance Criteria
- [ ] Xem danh sách địa chỉ đã lưu
- [ ] Thêm địa chỉ mới với nhãn và ghi chú
- [ ] Sửa, xóa địa chỉ
- [ ] Đặt địa chỉ mặc định
- [ ] Thông báo khi đạt tối đa 5 địa chỉ
- [ ] Địa chỉ mặc định hiển thị ⭐

## 6. Liên kết
- **Related:** TASK-005 Giỏ hàng & Đặt hàng (chọn địa chỉ)
