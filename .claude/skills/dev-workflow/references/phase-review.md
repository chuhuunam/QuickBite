# Phase 4: Review

## Input
- Code changes từ Phase 3

## Output
- Review report với severity levels
- Fixes cho tất cả blockers

## Actions

### 1. Chạy review
Dùng `/review` command hoặc gọi agent `code-reviewer`.

### 2. Review Checklist
- **Tính đúng đắn:** logic, null-safety, edge cases, exception handling
- **Security:** không hardcode secrets, validate input, prevent injection
- **Convention:** đúng layering, naming, coding standards
- **Performance:** N+1 queries, memory leaks, unnecessary loops
- **Test coverage:** có test cho code mới không

### 3. Severity Levels
- 🔴 **Blocker** - PHẢI fix trước khi merge
- 🟠 **Nên sửa** - Nên fix, có thể để technical debt
- 🟡 **Gợi ý** - Nice to have
- ✅ **Điểm tốt** - Những gì làm tốt

### 4. Fix Issues
- Fix tất cả 🔴 Blocker
- Cân nhắc fix 🟠 Nên sửa
- Commit fixes: `fix(scope): address review feedback`

## Gate
**KHÔNG được sang Phase 5 nếu còn 🔴 Blocker.**
