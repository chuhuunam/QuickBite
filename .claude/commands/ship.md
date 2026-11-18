---
description: Hoàn thành task - review, tạo PR, và merge
argument-hint: ""
---

Hoàn thành và ship task hiện tại:

1. Chạy `/review` tự động
2. Nếu có 🔴 Blocker → dừng lại, yêu cầu fix
3. Nếu không có blocker:
   - Tạo PR trên GitHub với `gh pr create`
   - PR title từ branch name
   - PR body từ commits + TODO.md
   - Link GitHub Issue nếu có (closes #xxx)
4. Thông báo PR URL cho user
5. Hỏi user có muốn merge không (sau khi team review)

```bash
# Tạo PR
gh pr create --title "<title>" --body "<body>"

# Sau khi approved, merge
gh pr merge --squash --delete-branch
```
