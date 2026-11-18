---
name: dev-workflow
description: >
  Quy trình phát triển 6 phases cho QuickBite: Phân tích → Thiết kế → Coding → Review → Test → Merge.
  Trigger: /dev command, "bắt đầu task", "làm feature", "implement"
---

# Dev Workflow - 6 Phases

Quy trình phát triển chuẩn cho team QuickBite.

## Phases Overview

| Phase | Tên | Output | Checkpoint |
|-------|-----|--------|------------|
| 1 | Phân tích | Scope + files cần thay đổi | User confirm |
| 2 | Thiết kế | Technical design | User approve |
| 3 | Coding | Code hoàn chỉnh | Self-check |
| 4 | Review | Review report | Fix blockers |
| 5 | Test | Tests pass | Coverage OK |
| 6 | Merge | PR merged | Team approve |

## Quy trình

### Bắt đầu task
1. Đọc file task từ `docs/ba/`
2. Tạo file `.todo/TODO-<task-name>.md`
3. Xác định services liên quan (BE/FE)
4. Load `coding-standards` phù hợp

### Thực hiện từng phase
Đọc chi tiết từng phase trong `references/`:
- `phase-analysis.md` - Phase 1
- `phase-design.md` - Phase 2
- `phase-coding.md` - Phase 3
- `phase-review.md` - Phase 4
- `phase-test.md` - Phase 5
- `phase-merge.md` - Phase 6

### Checkpoint rules
- **Phase 1 & 2:** PHẢI có user confirm trước khi tiếp tục
- **Phase 3:** Commit thường xuyên, conventional commits
- **Phase 4:** Fix tất cả 🔴 Blocker trước khi sang Phase 5
- **Phase 5:** Tests PHẢI pass 100%
- **Phase 6:** Team review trước khi merge

## TODO.md Template

```markdown
# TODO: <TASK-ID> - <Task Name>

## Task Info
- **Source:** docs/ba/<path>
- **Branch:** feature/<name>
- **Services:** [user-service, web-app, ...]
- **Started:** YYYY-MM-DD
- **Completed:** YYYY-MM-DD

## Phase Progress
- [ ] Phase 1: Phân tích
- [ ] Phase 2: Thiết kế
- [ ] Phase 3: Coding
- [ ] Phase 4: Review
- [ ] Phase 5: Test
- [ ] Phase 6: Merge

---

## Phase 1: Phân tích
**Scope:** <mô tả ngắn task làm gì>

**Files cần tạo/sửa:**
- file1.java - <lý do>
- file2.ts - <lý do>

---

## Phase 2: Thiết kế
**API Contract:**
- POST /api/xxx → Request/Response

**Data Model:**
- Entity: fields

**Classes:**
- Controller → Service → Repository

---

## Phase 3: Coding
**Commits:**
- `hash` message

**Files tạo:** X files

---

## Phase 4: Review
**Issues found:** X
**Fixed:** Y

**Fixes:**
1. ✅ Issue → Solution

---

## Phase 5: Test
**Test files:** X
**Coverage:** mô tả test cases

---

## Phase 6: Merge
**Pushed to:** <URL>
**Final commit:** `hash`

---

## Summary
| Metric | Value |
|--------|-------|
| Files | X |
| Commits | X |
| Tests | X |
```
