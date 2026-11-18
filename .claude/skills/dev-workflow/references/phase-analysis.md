# Phase 1: Phân tích (Analysis)

## Input
- File task từ `docs/ba/`
- Hoặc mô tả task từ user

## Output
- Hiểu rõ scope và acceptance criteria
- Danh sách files cần thay đổi
- Xác định services liên quan

## Actions

### 1. Đọc và hiểu task
- Đọc file task description
- Xác định acceptance criteria
- Liệt kê các yêu cầu chính

### 2. Phân tích impact
- Service nào bị ảnh hưởng? (BE/FE/cả hai)
- Files nào cần thay đổi?
- Có cần thay đổi database schema không?
- Có cần thêm API endpoints không?

### 3. Ghi vào TODO.md
```markdown
## Scope
- **Mô tả:** <tóm tắt task>
- **Services:** <danh sách services>
- **Files cần thay đổi:**
  - `path/to/file1.java` - <lý do>
  - `path/to/file2.ts` - <lý do>
- **Database changes:** <có/không>
- **New APIs:** <danh sách endpoints>
```

## Checkpoint
**DỪNG LẠI** và xác nhận với user:
- Scope có đúng không?
- Có thiếu gì không?
- Có câu hỏi nào cần làm rõ?

Chỉ tiếp tục Phase 2 sau khi user confirm.
