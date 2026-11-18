# Phase 5: Test

## Input
- Code đã qua review (không còn blockers)

## Output
- Unit tests pass
- Integration tests pass (nếu có)
- Coverage không giảm

## Actions

### 1. Backend Tests
```bash
# Chạy tests cho service cụ thể
cd services/<service-name>
mvn test

# Chạy với coverage
mvn test jacoco:report
```

### 2. Frontend Tests
```bash
# Chạy tests
cd web-app
ng test --watch=false --browsers=ChromeHeadless

# Chạy với coverage
ng test --code-coverage --watch=false
```

### 3. Test Checklist
- [ ] Happy path tests
- [ ] Edge cases tests
- [ ] Error handling tests
- [ ] Null/empty input tests

### 4. Viết tests nếu thiếu
Dùng agent `test-writer` nếu cần generate tests.

### 5. Verify Coverage
- Coverage không được giảm so với trước
- New code phải có coverage >= 80%

## Gate
**KHÔNG được sang Phase 6 nếu tests fail.**
