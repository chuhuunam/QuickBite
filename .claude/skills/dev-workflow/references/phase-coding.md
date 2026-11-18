# Phase 3: Coding

## Input
- Design đã được user approve

## Output
- Code hoàn chỉnh theo design
- Commits với conventional commits format

## Actions

### 1. Setup
- Checkout đúng branch
- Pull latest từ main (nếu cần)
- Mở files cần thay đổi

### 2. Coding Rules
- **Tuân theo coding-standards** - đọc BE/FE standards trước khi code
- **Commit thường xuyên** - mỗi logical unit là 1 commit
- **Conventional commits:**
  - `feat(scope): description` - tính năng mới
  - `fix(scope): description` - sửa bug
  - `refactor(scope): description` - refactor
  - `test(scope): description` - thêm test

### 3. Backend Checklist
- [ ] Controller đúng pattern
- [ ] Service có interface
- [ ] DTO cho request/response
- [ ] Exception handling
- [ ] Logging với SLF4J
- [ ] Không hardcode values

### 4. Frontend Checklist
- [ ] Standalone component
- [ ] Signals cho state
- [ ] takeUntilDestroyed cho subscriptions
- [ ] Error handling
- [ ] Loading states

### 5. Update TODO.md progress
Đánh dấu Phase 3 đang thực hiện, ghi notes nếu có vấn đề.

## Self-check trước Phase 4
- [ ] Code compile/build thành công
- [ ] Không có linting errors
- [ ] Đã commit tất cả changes
