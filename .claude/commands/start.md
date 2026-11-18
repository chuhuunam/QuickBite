---
description: Tạo branch mới từ GitHub Issue hoặc tên feature
argument-hint: "<github-issue-url hoặc tên-feature>"
---

Tạo branch mới cho task:

1. Nếu có GitHub Issue URL → fetch issue info
2. Tạo branch theo convention:
   - Feature: `feature/<issue>-<short-name>`
   - Bugfix: `fix/<issue>-<short-name>`
3. Checkout và push branch lên origin với `-u`
4. Tạo TODO.md với thông tin task

Input: $ARGUMENTS

Quy trình:
```bash
git fetch origin
git checkout main && git pull origin main
git checkout -b <branch-name>
git push -u origin <branch-name>
```

Thông báo kết quả cho user khi hoàn tất.
