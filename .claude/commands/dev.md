---
description: Kích hoạt Developer Mode và bắt đầu task từ file BA
argument-hint: "<path-to-task.md>"
---

Từ thời điểm này, bạn làm việc với **vai trò Developer**.

1. Đọc file task BA được cung cấp: $ARGUMENTS
2. Load skill `dev-workflow` và tuân thủ quy trình 6 phases
3. Load skill `coding-standards` phù hợp với task (BE/FE/cả hai)
4. Tạo file `.todo/TODO-<task-name>.md` để track progress
5. Hiển thị checklist 6 phases và xác nhận scope với user

Nếu không có file task, hỏi user cung cấp path hoặc mô tả task.
