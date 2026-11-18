# TODO: TASK-001 - Đăng ký Tài khoản

## Task Info
- **Source:** docs/ba/TASK-001-user-registration.md
- **Branch:** master (direct push)
- **Services:** user-service, common
- **Started:** 2026-11-18
- **Completed:** 2026-11-18

## Phase Progress
- [x] Phase 1: Phân tích ✅
- [x] Phase 2: Thiết kế ✅
- [x] Phase 3: Coding ✅
- [x] Phase 4: Review ✅
- [x] Phase 5: Test ✅
- [x] Phase 6: Merge ✅ DONE

---

## Phase 1: Phân tích
**Scope:**
- Tạo endpoint POST /api/auth/register
- Validate email format, password min 8 chars
- Hash password với BCrypt
- Return JWT tokens sau đăng ký

**Files cần tạo:**
- Parent POM + common module
- user-service: entity, repository, service, controller
- Security config, JWT utils

---

## Phase 2: Thiết kế

**API Contract:**
```
POST /api/auth/register
Request: { email, password, fullName }
Response: { accessToken, refreshToken, user }
Errors: 400 (validation), 409 (email exists)
```

**Data Model:**
- User entity: id, email, password, fullName, role, status, timestamps

**Classes:**
- AuthController → AuthService → UserRepository
- JwtUtils, PasswordEncoder, GlobalExceptionHandler

---

## Phase 3: Coding
**Commits:**
- `47f6610` feat(user-service): implement user registration endpoint

**Files tạo:** 23 files
- pom.xml (parent)
- common/ (3 files: ErrorResponse, exceptions)
- services/user-service/ (17 files)

---

## Phase 4: Review
**Issues found:** 10
**Fixed:** 6 (4 blockers + 2 should-fix)

**Fixes:**
1. ✅ TOCTOU race → Add DataIntegrityViolationException handler
2. ✅ JWT secret hardcoded → Use env var ${JWT_SECRET}
3. ✅ H2 console permitAll → Remove from main config
4. ✅ @Data on JPA entity → @Getter/@Setter + proper equals/hashCode
5. ✅ PII in logs → Remove email from controller log
6. ✅ Redundant email normalize → Keep @PrePersist only

**Commit:** `17d1e9f`

---

## Phase 5: Test
**Test files:** 2
- AuthServiceImplTest.java (4 test cases)
- AuthControllerTest.java (4 test cases)

**Coverage:**
- register success
- email normalization
- duplicate email → 409
- invalid email → 400
- short password → 400
- password hashing

**Commit:** `ffcada6`

---

## Phase 6: Merge
**Pushed to:** https://github.com/chuhuunam/QuickBite
**Final commit:** `9cb6da3`

---

## Summary
| Metric | Value |
|--------|-------|
| Files created | 26 |
| Lines added | ~1000 |
| Commits | 4 |
| Tests | 8 |
| Issues fixed | 6 |
