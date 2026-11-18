# TASK-004: Xem chi tiết Nhà hàng & Menu

## 1. Tổng quan
Khách hàng xem thông tin chi tiết nhà hàng và menu để chọn món đặt.

## 2. User Stories

### US-004.1: Xem chi tiết nhà hàng
> **As a** khách hàng
> **I want to** xem thông tin chi tiết của nhà hàng
> **So that** tôi biết thêm về nhà hàng trước khi đặt

**Thông tin hiển thị:**
- Ảnh bìa nhà hàng
- Tên nhà hàng
- Đánh giá trung bình và số lượt đánh giá
- Địa chỉ
- Giờ hoạt động
- Khoảng cách và thời gian giao hàng dự kiến

### US-004.2: Xem menu
> **As a** khách hàng
> **I want to** xem menu của nhà hàng
> **So that** tôi chọn được món muốn đặt

**Luồng chính:**
1. Khách vào trang chi tiết nhà hàng
2. Hệ thống hiển thị menu theo danh mục
3. Mỗi món hiển thị: Ảnh, Tên, Mô tả ngắn, Giá
4. Khách có thể nhấn vào món để xem chi tiết

### US-004.3: Thêm món vào giỏ
> **As a** khách hàng
> **I want to** thêm món vào giỏ hàng
> **So that** tôi có thể đặt nhiều món cùng lúc

**Luồng chính:**
1. Khách nhấn vào món ăn
2. Popup hiển thị chi tiết món: Ảnh lớn, mô tả, giá
3. Khách chọn số lượng
4. Khách có thể thêm ghi chú (ví dụ: "Không hành")
5. Nhấn "Thêm vào giỏ"
6. Hiển thị badge số lượng món ở icon giỏ hàng

**Luồng thay thế:**
- 5a. Giỏ hàng có món từ nhà hàng khác → Hỏi "Giỏ hàng có món từ [Nhà hàng X]. Bạn muốn xóa và thêm món mới?"

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Một nhà hàng/giỏ | Giỏ hàng chỉ chứa món từ một nhà hàng |
| BR-02 | Món hết hàng | Món hết hàng hiển thị xám, không cho thêm vào giỏ |
| BR-03 | Số lượng tối thiểu | Số lượng mỗi món tối thiểu là 1 |
| BR-04 | Số lượng tối đa | Số lượng mỗi món tối đa 99 |

## 4. Wireframe

### Màn hình Chi tiết Nhà hàng
```
┌─────────────────────────────┐
│ ← Phở 24               ❤️ 🛒│
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │      [Ảnh bìa]          │ │
│ └─────────────────────────┘ │
│ Phở 24                      │
│ ⭐ 4.5 (1,234 đánh giá)     │
│ 📍 123 Lê Lợi, Q1 • 1.2km   │
│ 🕐 20-30 phút               │
├─────────────────────────────┤
│ PHỞ                         │
│ ┌───────────┬─────────────┐ │
│ │ 🖼️        │ Phở Bò Tái  │ │
│ │           │ Phở với bò  │ │
│ │           │ 55,000đ     │ │
│ └───────────┴─────────────┘ │
│ ┌───────────┬─────────────┐ │
│ │ 🖼️        │ Phở Gà      │ │
│ │           │ Phở với gà  │ │
│ │           │ 50,000đ     │ │
│ └───────────┴─────────────┘ │
├─────────────────────────────┤
│ NƯỚC UỐNG                   │
│ ...                         │
└─────────────────────────────┘
```

### Popup Thêm món
```
┌─────────────────────────────┐
│ ┌─────────────────────────┐ │
│ │      [Ảnh món]          │ │
│ └─────────────────────────┘ │
│ Phở Bò Tái                  │
│ Phở với bò tái, hành,       │
│ rau thơm, chanh, ớt         │
│                             │
│ Ghi chú:                    │
│ ┌─────────────────────────┐ │
│ │ Không hành...           │ │
│ └─────────────────────────┘ │
│                             │
│ ┌───┐                       │
│ │ - │   2   │ + │           │
│ └───┘                       │
│                             │
│ ┌─────────────────────────┐ │
│ │ Thêm vào giỏ - 110,000đ │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

## 5. Acceptance Criteria
- [ ] Hiển thị đầy đủ thông tin nhà hàng
- [ ] Menu phân theo danh mục
- [ ] Nhấn món → Hiện popup thêm vào giỏ
- [ ] Có thể điều chỉnh số lượng và thêm ghi chú
- [ ] Cảnh báo khi thêm món từ nhà hàng khác
- [ ] Badge số lượng món trên icon giỏ hàng

## 6. Liên kết
- **Trước:** TASK-003 Danh sách nhà hàng
- **Sau:** TASK-005 Giỏ hàng & Đặt hàng
