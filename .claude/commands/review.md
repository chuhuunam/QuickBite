---
description: Claude review code changes trước khi tạo PR
argument-hint: "[path hoặc để trống = all changes]"
---

Review code với scope: $ARGUMENTS (nếu trống = tất cả changes)

1. Chạy `git diff` để xác định files changed
2. Load `coding-standards` tương ứng (BE/FE)
3. Review theo checklist:
   - Tính đúng đắn: logic, null-safety, edge cases
   - Security: không hardcode secrets, validate input
   - Convention: đúng layering, naming standards
   - Performance: N+1, memory leaks
   - Test coverage: có test cho code mới

Output format:
```
## Tóm tắt
<đánh giá tổng quan>

## 🔴 Blocker (phải sửa)
- file:line — vấn đề → đề xuất

## 🟠 Nên sửa
- ...

## 🟡 Gợi ý
- ...

## ✅ Điểm tốt
- ...
```

Nếu không có blocker, thông báo "Sẵn sàng tạo PR".
