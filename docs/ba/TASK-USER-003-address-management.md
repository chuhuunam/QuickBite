# TASK-USER-003: Quản lý Địa chỉ

## 1. Tổng quan
Khách hàng cần lưu và quản lý nhiều địa chỉ giao hàng. Mỗi địa chỉ có thể đặt làm mặc định để tiện đặt hàng nhanh.

## 2. User Stories

### US-USER-003.1: Thêm Địa chỉ mới
> **As a** khách hàng đã đăng nhập
> **I want to** thêm địa chỉ giao hàng mới
> **So that** tôi có thể chọn khi đặt hàng

**Luồng chính:**
1. Khách vào Cài đặt → Địa chỉ
2. Nhấn "Thêm địa chỉ"
3. Nhập thông tin: Nhãn, Địa chỉ chi tiết
4. (Tùy chọn) Chọn trên bản đồ để lấy tọa độ
5. Nhấn "Lưu"
6. Địa chỉ được thêm vào danh sách

**Luồng thay thế:**
- 3a. Địa chỉ đầu tiên → Tự động đặt làm mặc định

### US-USER-003.2: Xem danh sách Địa chỉ
> **As a** khách hàng đã đăng nhập
> **I want to** xem tất cả địa chỉ đã lưu
> **So that** tôi biết có những địa chỉ nào

**Luồng:**
1. Khách vào Cài đặt → Địa chỉ
2. Hiển thị danh sách địa chỉ
3. Địa chỉ mặc định có đánh dấu ⭐

### US-USER-003.3: Sửa Địa chỉ
> **As a** khách hàng
> **I want to** sửa địa chỉ đã lưu
> **So that** thông tin luôn chính xác

**Luồng:**
1. Khách chọn địa chỉ trong danh sách
2. Nhấn "Sửa"
3. Chỉnh sửa thông tin
4. Nhấn "Lưu"

### US-USER-003.4: Xóa Địa chỉ
> **As a** khách hàng
> **I want to** xóa địa chỉ không còn dùng
> **So that** danh sách gọn gàng

**Luồng:**
1. Khách chọn địa chỉ
2. Nhấn "Xóa"
3. Xác nhận "Bạn có chắc muốn xóa?"
4. Địa chỉ bị xóa

**Luồng thay thế:**
- 2a. Xóa địa chỉ mặc định → Địa chỉ tiếp theo thành mặc định
- 2b. Xóa địa chỉ duy nhất → Không còn địa chỉ mặc định

### US-USER-003.5: Đặt Địa chỉ Mặc định
> **As a** khách hàng
> **I want to** đặt một địa chỉ làm mặc định
> **So that** đặt hàng nhanh hơn

**Luồng:**
1. Khách chọn địa chỉ
2. Nhấn "Đặt làm mặc định"
3. Địa chỉ cũ bỏ mặc định, địa chỉ mới thành mặc định

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Giới hạn địa chỉ | Tối đa 10 địa chỉ/người dùng |
| BR-02 | Địa chỉ mặc định | Luôn có 1 địa chỉ mặc định (nếu có địa chỉ) |
| BR-03 | Nhãn địa chỉ | Nhãn gợi ý: Nhà, Công ty, Khác |
| BR-04 | Soft delete | Xóa mềm để giữ lịch sử đơn hàng |

## 4. Wireframe

### Màn hình Danh sách Địa chỉ
```
┌─────────────────────────────┐
│ ← Địa chỉ của tôi           │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ 🏠 Nhà               ⭐ │ │
│ │ 123 Nguyễn Văn A, Q.1   │ │
│ │ TP. Hồ Chí Minh         │ │
│ │                   ✏️ 🗑 │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🏢 Công ty              │ │
│ │ 456 Lê Lai, Q.3         │ │
│ │ TP. Hồ Chí Minh         │ │
│ │                   ✏️ 🗑 │ │
│ └─────────────────────────┘ │
│                             │
│  ┌───────────────────────┐  │
│  │   + Thêm địa chỉ      │  │
│  └───────────────────────┘  │
└─────────────────────────────┘
```

### Màn hình Thêm/Sửa Địa chỉ
```
┌─────────────────────────────┐
│ ← Thêm địa chỉ              │
├─────────────────────────────┤
│                             │
│  Nhãn                       │
│  ┌───────────────────────┐  │
│  │ Nhà ▼                 │  │
│  └───────────────────────┘  │
│                             │
│  Địa chỉ chi tiết           │
│  ┌───────────────────────┐  │
│  │ 123 Nguyễn Văn A      │  │
│  │ Phường 1, Quận 1      │  │
│  │ TP. Hồ Chí Minh       │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │ 📍 Chọn trên bản đồ   │  │
│  └───────────────────────┘  │
│                             │
│  ☐ Đặt làm địa chỉ mặc định │
│                             │
│  ┌───────────────────────┐  │
│  │         LƯU           │  │
│  └───────────────────────┘  │
└─────────────────────────────┘
```

## 5. Acceptance Criteria
- [ ] Hiển thị danh sách địa chỉ của user
- [ ] Thêm địa chỉ mới với nhãn và chi tiết
- [ ] Sửa địa chỉ đã có
- [ ] Xóa địa chỉ (soft delete)
- [ ] Đặt địa chỉ mặc định
- [ ] Địa chỉ đầu tiên tự động thành mặc định
- [ ] Giới hạn tối đa 10 địa chỉ
- [ ] Lưu tọa độ lat/lng (optional)

## 6. Liên kết
- **Trước:** TASK-002 Đăng nhập
- **Sau:** TASK-003 Duyệt nhà hàng
