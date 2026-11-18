# TASK-003: Xem danh sách Nhà hàng

## 1. Tổng quan
Khách hàng cần tìm và chọn nhà hàng để đặt món. Hệ thống hiển thị danh sách nhà hàng với khả năng tìm kiếm và lọc.

## 2. User Stories

### US-003.1: Xem danh sách nhà hàng
> **As a** khách hàng
> **I want to** xem danh sách các nhà hàng
> **So that** tôi có thể chọn nơi đặt món

**Luồng chính:**
1. Khách mở app/vào trang chủ
2. Hệ thống hiển thị danh sách nhà hàng
3. Mỗi nhà hàng hiển thị: Ảnh, Tên, Đánh giá, Danh mục, Khoảng cách
4. Khách cuộn để xem thêm (infinite scroll)

### US-003.2: Tìm kiếm nhà hàng
> **As a** khách hàng
> **I want to** tìm kiếm nhà hàng theo tên
> **So that** tôi nhanh chóng tìm được nơi muốn đặt

**Luồng chính:**
1. Khách nhấn vào ô tìm kiếm
2. Nhập từ khóa (ví dụ: "phở", "pizza")
3. Hệ thống hiển thị kết quả match với tên nhà hàng hoặc món ăn
4. Khách chọn nhà hàng từ kết quả

### US-003.3: Lọc nhà hàng
> **As a** khách hàng
> **I want to** lọc nhà hàng theo tiêu chí
> **So that** tôi tìm được nhà hàng phù hợp

**Bộ lọc:**
- Đánh giá: 4+ sao, 4.5+ sao
- Khoảng cách: Gần nhất, < 3km, < 5km
- Loại ẩm thực: Việt Nam, Hàn Quốc, Nhật Bản, Pizza...
- Sắp xếp: Phổ biến, Đánh giá cao, Gần nhất

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Chỉ hiện active | Chỉ hiển thị nhà hàng đang hoạt động |
| BR-02 | Sắp xếp mặc định | Mặc định sắp xếp theo khoảng cách gần nhất |
| BR-03 | Pagination | Load 10 nhà hàng mỗi lần cuộn |

## 4. Wireframe

### Màn hình Danh sách Nhà hàng
```
┌─────────────────────────────┐
│  🔍 Tìm nhà hàng, món ăn    │
├─────────────────────────────┤
│ [Gần đây] [Đánh giá] [Lọc]  │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ 🖼️                      │ │
│ │ Phở 24                  │ │
│ │ ⭐ 4.5 • Phở • 1.2km    │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🖼️                      │ │
│ │ Pizza Hut               │ │
│ │ ⭐ 4.2 • Pizza • 2.5km  │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ 🖼️                      │ │
│ │ Cơm Tấm Sài Gòn         │ │
│ │ ⭐ 4.8 • Cơm • 0.8km    │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 5. Thông tin hiển thị mỗi nhà hàng

| Thông tin | Mô tả |
|-----------|-------|
| Ảnh đại diện | Ảnh nhà hàng hoặc món signature |
| Tên | Tên nhà hàng |
| Đánh giá | Số sao trung bình (1-5) |
| Danh mục | Loại ẩm thực chính |
| Khoảng cách | Khoảng cách từ vị trí người dùng |
| Tag (optional) | "Mới", "Khuyến mãi", "Phổ biến" |

## 6. Acceptance Criteria
- [ ] Hiển thị danh sách nhà hàng với ảnh, tên, đánh giá
- [ ] Tìm kiếm theo tên nhà hàng
- [ ] Lọc theo đánh giá và khoảng cách
- [ ] Infinite scroll load thêm nhà hàng
- [ ] Nhấn vào nhà hàng → Chuyển đến chi tiết

## 7. Liên kết
- **Sau:** TASK-004 Xem chi tiết nhà hàng
- **Related:** TASK-005 Quản lý giỏ hàng
