# QuickBite - Tài liệu Nghiệp vụ (BA)

## Giới thiệu
Thư mục này chứa các tài liệu Business Analysis mô tả luồng chức năng của hệ thống QuickBite.

## Cấu trúc tài liệu

Mỗi tài liệu BA bao gồm:
1. **Tổng quan** - Mô tả ngắn gọn chức năng
2. **User Stories** - Các câu chuyện người dùng với luồng chi tiết
3. **Business Rules** - Quy tắc nghiệp vụ
4. **Wireframe** - Mockup giao diện (text-based)
5. **Acceptance Criteria** - Tiêu chí chấp nhận
6. **Liên kết** - Các task liên quan

## Danh sách Task theo Chức năng

### Người dùng & Xác thực
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-001](TASK-001-user-registration.md) | Đăng ký tài khoản | Khách hàng |
| [TASK-002](TASK-002-user-login.md) | Đăng nhập | Khách hàng |
| [TASK-008](TASK-008-user-addresses.md) | Quản lý địa chỉ | Khách hàng |

### Đặt món & Nhà hàng
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-003](TASK-003-browse-restaurants.md) | Xem danh sách nhà hàng | Khách hàng |
| [TASK-004](TASK-004-restaurant-detail.md) | Chi tiết nhà hàng & Menu | Khách hàng |
| [TASK-005](TASK-005-cart-checkout.md) | Giỏ hàng & Đặt hàng | Khách hàng |

### Đơn hàng
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-006](TASK-006-order-tracking.md) | Theo dõi đơn hàng | Khách hàng |
| [TASK-007](TASK-007-order-history.md) | Lịch sử đơn hàng | Khách hàng |

### Thanh toán
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-009](TASK-009-wallet.md) | Ví điện tử | Khách hàng |
| [TASK-012](TASK-012-payment-processing.md) | Xử lý thanh toán | Khách hàng |

### Tài xế
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-010](TASK-010-driver-app.md) | Luồng tài xế | Tài xế |

### Nhà hàng
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-011](TASK-011-restaurant-owner.md) | Luồng chủ nhà hàng | Chủ nhà hàng |

### Hệ thống
| Task | Mô tả | Actor |
|------|-------|-------|
| [TASK-013](TASK-013-notifications.md) | Hệ thống thông báo | Tất cả |

## Actors

| Actor | Mô tả |
|-------|-------|
| Khách hàng | Người đặt đồ ăn |
| Tài xế | Người giao hàng |
| Chủ nhà hàng | Người quản lý nhà hàng |
| Admin | Quản trị viên hệ thống |

## Luồng chính: Đặt hàng

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Đăng ký/  │───►│    Chọn     │───►│   Thêm vào  │
│  Đăng nhập  │    │  nhà hàng   │    │   giỏ hàng  │
└─────────────┘    └─────────────┘    └─────────────┘
                                             │
                                             ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Nhận hàng  │◄───│  Theo dõi   │◄───│   Thanh    │
│  & Đánh giá │    │  đơn hàng   │    │   toán     │
└─────────────┘    └─────────────┘    └─────────────┘
```

## Sử dụng với Claude Dev Workflow

```bash
# Bắt đầu task
/dev docs/ba/TASK-001-user-registration.md

# Review code
/review

# Tạo PR
/ship
```

## Template

Xem [_template.md](_template.md) để tạo task mới.
